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
package ksch.patientmanagement.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @PostMapping("/api/patients")
    public PatientEntity createPatient() {
        return patientRepository.save(PatientEntity.builder()
                .name("John Doe")
                .gender("MALE")
                .address("Guesthouse")
                .dateOfBirth(LocalDate.now().minusYears(25))
                .build());
    }

    @GetMapping("/api/patients")
    public Iterable<PatientEntity> getPatient() {
        return patientRepository.findAll();
    }
}
