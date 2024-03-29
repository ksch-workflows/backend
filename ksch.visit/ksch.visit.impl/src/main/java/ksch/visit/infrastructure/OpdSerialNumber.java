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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link OpdSerialNumber} is a utility class for the generation of OPD numbers.
 * <p>
 * It represents the numeric part of an OPD number which gets incremented every time a visit gets created.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TableGenerator(name = "numeric_part_of_opd_number", initialValue = 999)
public class OpdSerialNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "numeric_part_of_opd_number")
    @Column(unique = true, name = "`value`")
    private int value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
