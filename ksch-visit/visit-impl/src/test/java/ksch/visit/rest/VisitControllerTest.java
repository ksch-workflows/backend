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

import ksch.patientmanagement.PatientService;
import ksch.testing.RestControllerTest;
import ksch.testing.TestResource;
import ksch.visit.domain.JohnDoe;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitControllerTest extends RestControllerTest {

    @Autowired
    private PatientService patientService;

    @Test
    @SneakyThrows
    public void should_start_visit() {
        var patient = patientService.createPatient(new JohnDoe());
        mockMvc.perform(
                post(String.format("/api/patients/%s/visits", patient.getId()))
                        .content(new TestResource("start-visit.json").readString())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("_id", is(notNullValue())))
                .andDo(document("visit.start-visit"));
        // TODO Check that start time is rendered as ISO string
    }

    @Test
    @SneakyThrows
    public void should_fail_to_start_visit_if_there_is_already_a_visit() {

    }
}