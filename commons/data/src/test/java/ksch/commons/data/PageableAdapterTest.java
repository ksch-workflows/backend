package ksch.commons.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

public class PageableAdapterTest {

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
