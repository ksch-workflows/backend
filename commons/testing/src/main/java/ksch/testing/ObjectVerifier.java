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
package ksch.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.opentest4j.AssertionFailedError;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ObjectVerifier {

    /**
     * This method helps to check that no optional field gets forgotten when writing tests for data type
     * converter methods.
     */
    @SneakyThrows
    public static void verifyAllFieldsAreSet(Object object) {
        var objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(object);
        if (json.contains("null")) {
            var message = "The provided object contains one or more fields that are not initialized: " + json;
            throw new AssertionFailedError(message);
        }
    }
}
