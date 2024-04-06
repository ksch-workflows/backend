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
package ksch.patientmanagement.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import ksch.commons.data.PageFacade;
import ksch.commons.data.PageableAdapter;
import ksch.patientmanagement.api.Patient;
import ksch.patientmanagement.core.PatientRepository;
import ksch.patientmanagement.core.PatientService;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@Transactional
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientJpaRepository patientJpaRepository;

    @Autowired
    private PatientService patientService;

    private Patient johnDoe;

    @BeforeEach
    public void setup() {
        patientJpaRepository.deleteAll();
        johnDoe = patientService.createPatient(new JohnDoe());
    }

    @Test
    @DisplayName("Should find existing patient by ID")
    void test_findById_existing_patient() {
        final Optional<Patient> result = patientRepository.findById(johnDoe.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(johnDoe);
    }

    @Test
    @DisplayName("Should not find patient by random ID")
    void test_findById_non_existent_patient() {
        final Optional<Patient> result = patientRepository.findById(UUID.randomUUID());

        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("Should get page of all existing patients")
    void test_findAll_page() {
        final PageRequest pageRequest = PageRequest.of(0, 10);

        final PageFacade<Patient> result = patientRepository.findAll(new PageableAdapter(pageRequest));

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Should find patient by ID")
    void test_search_by_patient_id() {
        final PageRequest pageRequest = PageRequest.of(0, 10);

        final PageFacade<Patient> result = patientRepository.search(
            johnDoe.getId().toString(), new PageableAdapter(pageRequest)
        );

        assertThat(result.getContent()).hasSize(1);
    }
}
