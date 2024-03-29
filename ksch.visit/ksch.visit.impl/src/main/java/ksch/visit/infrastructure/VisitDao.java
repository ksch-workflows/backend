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

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ksch.visit.Visit;
import ksch.visit.VisitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VisitDao implements Visit {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true, columnDefinition = "uuid")
    private UUID id;

    private String opdNumber;

    @Column(columnDefinition = "uuid")
    private UUID patientId;

    private VisitType type;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    public static VisitDao from(Visit visit) {
        return VisitDao.builder()
                .id(visit.getId())
                .opdNumber(visit.getOpdNumber())
                .patientId(visit.getPatientId())
                .type(visit.getType())
                .timeStart(visit.getTimeStart())
                .timeEnd(visit.getTimeEnd())
                .build();
    }
}
