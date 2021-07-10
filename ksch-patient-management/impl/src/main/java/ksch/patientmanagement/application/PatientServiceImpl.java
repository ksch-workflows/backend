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
package ksch.patientmanagement.application;

import ksch.patientmanagement.Patient;
import ksch.patientmanagement.PatientService;
import ksch.patientmanagement.infrastructure.PatientDao;
import ksch.patientmanagement.infrastructure.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientJpaRepository patientRepository;

    public Patient createPatient() {
        return createPatient(new PatientDao());
    }

    public Patient createPatient(Patient patient) {
        return createPatient(PatientDao.from(patient));
    }

    private Patient createPatient(PatientDao patient) {
        return patientRepository.save(patient);
    }
}
