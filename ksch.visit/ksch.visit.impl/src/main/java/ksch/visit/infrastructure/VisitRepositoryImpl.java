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
package ksch.visit.infrastructure;

import ksch.visit.Visit;
import ksch.visit.domain.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VisitRepositoryImpl implements VisitRepository {

    private final VisitJpaRepository visitJpaRepository;

    @Override
    public Optional<Visit> findCurrentVisit(UUID patientId) {
        return visitJpaRepository.findCurrentVisit(patientId);
    }

    @Override
    public Visit save(Visit visit) {
        return visitJpaRepository.save(VisitDao.from(visit));
    }

    @Override
    public Optional<Visit> get(UUID patientId, UUID visitId) {
        return visitJpaRepository.findByIdAndPatientId(visitId, patientId);
    }
}
