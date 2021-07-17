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
package ksch.patientmanagement.infrastructure;

import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;

import java.util.UUID;

import static ksch.patientmanagement.Gender.MALE;

public class JohnDoe implements Patient {

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getPatientNumber() {
        return "2021-1005";
    }

    @Override
    public String getName() {
        return "John Doe";
    }

    @Override
    public Integer getAge() {
        return 19;
    }

    @Override
    public Gender getGender() {
        return MALE;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getResidentialAddress() {
        return "Guesthouse";
    }

    @Override
    public String getPatientCategory() {
        return null;
    }
}
