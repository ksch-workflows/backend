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
package ksch.patientmanagement.web;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.oai.validator.OpenApiInteractionValidator;

import ksch.patientmanagement.core.PatientService;
import ksch.testing.OasValidatorFactory;
import ksch.testing.RestControllerTest;
import ksch.testing.TestResource;
import lombok.SneakyThrows;

class PatientControllerTest extends RestControllerTest {

    private static final OpenApiInteractionValidator validator = OasValidatorFactory.createValidator(
        "../../docs/openapi.yml"
    );

    @Autowired
    private PatientService patientService;

    @Test
    @SneakyThrows
    void should_create_patient_without_payload() {
        mockMvc.perform(post("/api/patients").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_id", is(notNullValue())))
                .andDo(print())
                .andDo(document("patients-create-emergency"))
                .andExpect(openApi().isValid(validator))
        ;
    }

    @Test
    @SneakyThrows
    void should_create_patient_with_payload() {
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
                .andExpect(jsonPath("phoneNumber", is(equalTo("0123456789"))))
                .andExpect(jsonPath("residentialAddress", is(equalTo("Guesthouse"))))
                .andDo(document("patients-create-normal"))
                .andExpect(openApi().isValid(validator))
        ;
    }

    @Test
    @SneakyThrows
    void should_get_patient() {
        var patient = patientService.createPatient();

        mockMvc.perform(get("/api/patients/{patientId}", patient.getId()).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("patients-get"))
                .andExpect(openApi().isValid(validator))
        ;
    }

    @Test
    @SneakyThrows
    void should_list_patients() {
        patientService.createPatient();
        patientService.createPatient();

        mockMvc.perform(get("/api/patients").accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements", is(greaterThanOrEqualTo(2))))
                .andDo(document("patients-list"))
                .andExpect(openApi().isValid(validator))
        ;
    }

    @Test
    @SneakyThrows
    void should_search_patients() {
        var searchedPatient = patientService.createPatient();
        patientService.createPatient(searchedPatient);

        mockMvc.perform(get("/api/patients/search?q=" + searchedPatient.getId().toString()).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements", is(equalTo(1))))
                .andDo(document("patients-search"))
                .andExpect(openApi().isValid(validator))
        ;
    }
}
