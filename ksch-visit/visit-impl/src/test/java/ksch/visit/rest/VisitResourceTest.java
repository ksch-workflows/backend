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

import ksch.visit.VisitType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static ksch.testing.ObjectVerifier.verifyAllFieldsAreSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class VisitResourceTest {

    @Test
    public void should_create_object_from_interface() {
        var originalObject = VisitResource.builder()
                .id(UUID.randomUUID())
                .opdNumber("10-1234")
                .patientId(UUID.randomUUID())
                .type(VisitType.IPD)
                .timeStart(now().minus(1, DAYS))
                .timeEnd(now())
                .build();
        verifyAllFieldsAreSet(originalObject);

        var convertedObject = VisitResource.from(originalObject);

        assertThat(convertedObject, equalTo(originalObject));
    }
}
