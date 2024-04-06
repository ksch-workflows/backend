/*
 * Copyright 2024 KS-plus e.V.
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
package ksch.patientmanagement.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ksch.patientmanagement.api.Patient;

@SpringBootTest
@ExtendWith({SpringExtension.class})
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    @DisplayName("Should create patients without knowing anything about them")
    void test_createPatient_without_details() {
        final Patient patient = patientService.createPatient();

        assertThat(patient.getId()).isNotNull();
        assertThat(patient.getName()).isNull();
    }

    @Test
    @DisplayName("Should create patient with provided details")
    void test_createPatient_with_provided_details() {
        final PatientEntity patientEntity = PatientEntity.builder()
            .name("John Doe")
            .age(42)
            .build();

        final Patient createdPatient = patientService.createPatient(patientEntity);

        assertThat(createdPatient.getId()).isNotNull();
        assertThat(PatientEntity.from(createdPatient)).isEqualTo(patientEntity.withId(createdPatient.getId()));
    }
}
