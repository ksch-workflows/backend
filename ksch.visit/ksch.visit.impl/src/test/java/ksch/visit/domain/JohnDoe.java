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
package ksch.visit.domain;

import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;

import java.util.UUID;

public class JohnDoe implements Patient {

    private static final UUID ID = UUID.randomUUID();

    @Override
    public UUID getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "John Doe";
    }

    @Override
    public Integer getAge() {
        return 25;
    }

    @Override
    public Gender getGender() {
        return Gender.MALE;
    }

    @Override
    public String getPhoneNumber() {
        return "0123456789";
    }

    @Override
    public String getResidentialAddress() {
        return "Guesthouse";
    }

    @Override
    public String getPatientCategory() {
        return "GENERAL";
    }
}
