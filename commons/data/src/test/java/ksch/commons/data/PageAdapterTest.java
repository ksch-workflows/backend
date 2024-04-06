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

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

class PageAdapterTest {

    @Test
    @DisplayName("Should delegate method calls to PageImpl")
    void test_delegate_method_calls() {
        final List<String> pageContents = List.of("Foo", "Bar", "Baz");
        final PageImpl<String> page = new PageImpl<>(pageContents);

        final PageAdapter<String> pageAdapter = new PageAdapter<>(page);

        assertEquals(page.getTotalPages(), pageAdapter.getTotalPages());
        assertEquals(page.getTotalElements(), pageAdapter.getTotalElements());
        assertEquals(page.getNumber(), pageAdapter.getNumber());
        assertEquals(page.getSize(), pageAdapter.getSize());
        assertEquals(page.getNumberOfElements(), pageAdapter.getNumberOfElements());
        assertEquals(page.getContent(), pageAdapter.getContent());
        assertEquals(page.hasContent(), pageAdapter.hasContent());
        assertEquals(page.getSort(), pageAdapter.getSort());
        assertEquals(page.isFirst(), pageAdapter.isFirst());
        assertEquals(page.isLast(), pageAdapter.isLast());
        assertEquals(page.hasNext(), pageAdapter.hasNext());
        assertEquals(page.hasPrevious(), pageAdapter.hasPrevious());
        assertEquals(page.nextPageable(), pageAdapter.nextPageable());
        assertEquals(page.previousPageable(), pageAdapter.previousPageable());
        assertEquals(page.map(e -> 42), pageAdapter.map(e -> 42));
    }
}
