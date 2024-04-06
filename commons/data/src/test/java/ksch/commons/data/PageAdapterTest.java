package ksch.commons.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

public class PageAdapterTest {

    @Test
    @DisplayName("Should delegate method calls to PageImpl")
    void test_delegate_method_calls() {
        final List<String> pageContents = List.of("Foo", "Bar", "Baz");
        final PageImpl<String> page = new PageImpl<>(pageContents);

        final PageAdapter<String> pageAdapter = new PageAdapter<>(page);

        assertEquals(page.getTotalPages(), pageAdapter.getTotalPages());
        assertEquals(page.getTotalElements(), pageAdapter.getTotalElements());
        assertEquals(page.map(e -> 42), pageAdapter.map(e -> 42));
    }
}
