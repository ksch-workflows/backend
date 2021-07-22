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
package ksch.visit.rest;

import ksch.testing.RestControllerTest;
import ksch.testing.TestResource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitControllerTest extends RestControllerTest {

    @Test
    @SneakyThrows
    public void should_start_visit() {
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
    public void should_fail_to_start_visit_if_there_is_already_a_visit() {

    }
}
