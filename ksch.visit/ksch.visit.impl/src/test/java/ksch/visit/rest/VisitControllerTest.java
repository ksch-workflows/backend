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

import com.fasterxml.jackson.databind.ObjectMapper;
import ksch.patientmanagement.PatientService;
import ksch.testing.RestControllerTest;
import ksch.testing.TestResource;
import ksch.visit.VisitService;
import ksch.visit.VisitType;
import ksch.visit.domain.JohnDoe;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitControllerTest extends RestControllerTest {

    public static final String ISO_8601_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d*";

    @Autowired
    private PatientService patientService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(jsonPath("_id", is(notNullValue())))
                .andExpect(jsonPath("opdNumber", is(notNullValue())))
                .andExpect(jsonPath("type", is("OPD")))
                .andExpect(jsonPath("timeStart", matchesPattern(ISO_8601_PATTERN)))
                .andDo(document("start-visit"))
        ;
    }

    @Test
    @SneakyThrows
    public void should_get_visit() {
        var patientId = patientService.createPatient(new JohnDoe()).getId();
        var visitId = visitService.startVisit(patientId, VisitType.EMERGENCY).getId();

        mockMvc.perform(
                        get(String.format("/api/patients/%s/visits/%s", patientId, visitId))
                                .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("opdNumber", is(notNullValue())))
                .andExpect(jsonPath("type", is("EMERGENCY")))
                .andExpect(jsonPath("timeStart", matchesPattern(ISO_8601_PATTERN)))
                .andDo(document("get-visit"))
        ;
    }

    @Test
    @SneakyThrows
    public void should_return_not_found_for_unknown_visit() {
        mockMvc.perform(
                        get(String.format("/api/patients/%s/visits/%s", UUID.randomUUID(), UUID.randomUUID()))
                                .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", Matchers.containsString("Could not find visit")))
        ;
    }
}
