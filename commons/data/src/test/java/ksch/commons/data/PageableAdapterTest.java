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
package ksch.commons.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

class PageableAdapterTest {

    @Test
    @DisplayName("Should delegate method calls to PageRequest implementation")
    void test_delegate_method_calls() {
        final PageRequest pageRequest = PageRequest.of(0, 10);

        final PageableAdapter pageableAdapter = new PageableAdapter(pageRequest);

        assertEquals(pageRequest.getPageNumber(), pageableAdapter.getPageNumber());
        assertEquals(pageRequest.getPageSize(), pageableAdapter.getPageSize());
        assertEquals(pageRequest.getOffset(), pageableAdapter.getOffset());
        assertEquals(pageRequest.getSort(), pageableAdapter.getSort());
        assertEquals(pageRequest.next(), pageableAdapter.next());
        assertEquals(pageRequest.previousOrFirst(), pageableAdapter.previousOrFirst());
        assertEquals(pageRequest.first(), pageableAdapter.first());
        assertEquals(pageRequest.withPage(0), pageableAdapter.withPage(0));
        assertEquals(pageRequest.hasPrevious(), pageableAdapter.hasPrevious());
    }
}
