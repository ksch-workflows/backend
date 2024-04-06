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
package ksch.patientmanagement.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import ksch.patientmanagement.api.Gender;
import ksch.patientmanagement.api.Patient;
import ksch.util.TypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
class PatientResource extends RepresentationModel<PatientResource> implements Patient {

    @JsonProperty("_id")
    private UUID id;

    private String patientNumber;

    private String name;

    private Integer age;

    private Gender gender;

    private String phoneNumber;

    private String residentialAddress;

    private String patientCategory;

    static PatientResource from(Patient patient) {
        return new TypeConverter<>(Patient.class).convertTo(patient, PatientResource.class);
    }
}
