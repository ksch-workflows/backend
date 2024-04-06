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
import ksch.visit.VisitType;
import ksch.visit.domain.VisitCannotBeStartedException;
import ksch.visit.domain.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @InjectMocks
    VisitServiceImpl visitService;

    @Mock
    VisitRepository visitRepository;

    @Test
    public void should_reject_to_start_visit_if_there_is_already_an_active_visit() {
        when(visitRepository.findCurrentVisit(any(UUID.class))).thenReturn(Optional.of(Mockito.mock(Visit.class)));

        assertThrows(
                VisitCannotBeStartedException.class,
                () -> visitService.startVisit(UUID.randomUUID(), VisitType.IPD)
        );
    }
}
