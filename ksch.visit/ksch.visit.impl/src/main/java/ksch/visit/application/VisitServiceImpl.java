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
package ksch.visit.application;

import ksch.visit.Visit;
import ksch.visit.VisitService;
import ksch.visit.VisitType;
import ksch.visit.domain.VisitCannotBeStartedException;
import ksch.visit.domain.VisitRepository;
import ksch.visit.infrastructure.VisitDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final OpdNumberGenerator opdNumberGenerator;

    @Override
    public Visit startVisit(UUID patientId, VisitType type) {

        if (visitRepository.hasActiveVisit(patientId)) {
            var message = "Visit cannot be started because there is already an active visit for patient with ID '" +
                    patientId + "'.";
            throw new VisitCannotBeStartedException(message);
        }
        var opdNumber = opdNumberGenerator.generateOpdNumber();

        return visitRepository.save(VisitDao.builder()
                .opdNumber(opdNumber)
                .patientId(patientId)
                .type(type)
                .timeStart(now())
                .build());
    }
}
