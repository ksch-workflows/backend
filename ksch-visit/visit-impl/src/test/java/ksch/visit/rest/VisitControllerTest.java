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
import ksch.visit.domain.JohnDoe;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitControllerTest extends RestControllerTest {

    public static final String ISO_8601_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d*";

    @Autowired
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper2; // TODO Use actual object mapper

    @Test
    @SneakyThrows
    public void should_deserialize_date() {
        var result = objectMapper2.convertValue(LocalDateTime.now(), String.class);

        assertThat(result, matchesRegex(ISO_8601_PATTERN));
    }

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
                .andExpect(jsonPath("timeStart", matchesPattern(ISO_8601_PATTERN)))
                .andDo(document("visit_start-visit"));
        // TODO Assert for all visit properties
        // TODO Include visit snippets in API docs
    }
}
