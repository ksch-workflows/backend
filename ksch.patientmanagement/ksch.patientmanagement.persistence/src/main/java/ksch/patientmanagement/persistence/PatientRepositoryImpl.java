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

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import ksch.commons.data.PageFacade;
import ksch.commons.data.PageAdapter;
import ksch.commons.data.PageableFacade;
import ksch.patientmanagement.api.Patient;
import ksch.patientmanagement.core.PatientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    @Override
    public Patient save(Patient patient) {
        return patientJpaRepository.save(PatientDao.from(patient));
    }

    @Override
    public Optional<Patient> findById(UUID patientId) {
        return patientJpaRepository.findById(patientId).map(p -> p);
    }

    @Override
    public PageFacade<Patient> findAll(PageableFacade pageable) {
        return new PageAdapter<>(patientJpaRepository.findAll(pageable).map(p -> p));
    }

    @Override
    public PageFacade<Patient> search(String query, PageableFacade pageable) {
        return new PageAdapter<>(patientJpaRepository
            .findAll(new PatientSearchSpecification(query), pageable).map(p -> p));
    }
}
