/*
 * Copyright 2021 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch.patientmanagement.rest;

import ksch.patientmanagement.PatientService;
import ksch.test.TestResource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PatientControllerTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .snippets()
                        .withDefaults(
                                curlRequest(),
                                httpResponse()
                        )
                ).build();
    }

    @Test
    @SneakyThrows
    public void should_create_patient_without_payload() {
        mockMvc.perform(post("/api/patients").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_id", is(notNullValue())))
                .andDo(print())
                .andDo(document("patients-create-emergency"));
    }

    @Test
    @SneakyThrows
    public void should_create_patient_with_payload() {
        var payload = new TestResource("create-patient.json").readString();
        mockMvc.perform(
                post("/api/patients")
                        .content(payload)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_id", is(notNullValue())))
                .andExpect(jsonPath("_links.self.href", is(startsWith("http://localhost:8080/api/patients/"))))
                .andExpect(jsonPath("gender", is(equalTo("MALE"))))
                .andExpect(jsonPath("name", is(equalTo("John Doe"))))
                .andExpect(jsonPath("patientCategory", is(equalTo("GENERAL"))))
                .andExpect(jsonPath("patientNumber", is(equalTo("10-1002"))))
                .andExpect(jsonPath("phoneNumber", is(equalTo("0123456789"))))
                .andExpect(jsonPath("residentialAddress", is(equalTo("Guesthouse"))))
                .andDo(document("patients-create-normal"));
    }

    @Test
    @SneakyThrows
    public void should_list_patients() {
        patientService.createPatient();
        patientService.createPatient();

        mockMvc.perform(get("/api/patients").accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements", is(greaterThanOrEqualTo(2))))
                .andDo(document("patients-list"));
    }

    @Test
    @SneakyThrows
    public void should_search_patients() {
        mockMvc.perform(get("/api/patients/search?q=John%20Doe").accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements", is(greaterThanOrEqualTo(2))))
                .andDo(document("patients-search"));
    }
}
