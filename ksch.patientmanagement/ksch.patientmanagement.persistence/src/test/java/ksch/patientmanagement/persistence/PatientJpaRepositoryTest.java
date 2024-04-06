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
package ksch.patientmanagement.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import ksch.patientmanagement.api.Patient;
import ksch.patientmanagement.core.PatientRepository;
import ksch.patientmanagement.core.PatientService;

// TODO Use JPA test only
@SpringBootTest
@ExtendWith({SpringExtension.class})
@Transactional
class PatientJpaRepositoryTest {

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

        patientService.createPatient();
        patientService.createPatient(new JaneDoe());
        johnDoe = patientService.createPatient(new JohnDoe());
    }

    @Test
    public void should_find_patients_by_last_name() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification("Doe"));
        assertEquals(2, result.size());
    }

    @Test
    public void should_find_no_results_for_unknown_patient_name() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification("Max Mustermann"));
        assertEquals(0, result.size());
    }

    @Test
    public void should_find_patient_by_full_name() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification("John Doe"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_full_name_case_insensitive() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification("john doe"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_partial_name() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification("john"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_id() {
        var result = patientJpaRepository.findAll(new PatientSearchSpecification(johnDoe.getId().toString()));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }
}
