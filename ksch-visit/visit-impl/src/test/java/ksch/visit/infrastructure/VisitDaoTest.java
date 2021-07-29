package ksch.visit.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksch.visit.VisitType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class VisitDaoTest {

    @Test
    public void should_build_entity_from_interface() {
        var originalEntity = VisitDao.builder()
                .id(UUID.randomUUID())
                .opdNumber("10-1234")
                .patientId(UUID.randomUUID())
                .type(VisitType.IPD)
                .timeStart(now().minus(1, DAYS))
                .timeEnd(now())
                .build();
        verifyNoFieldHasNullValue(originalEntity);

        var convertedEntity = VisitDao.from(originalEntity);

        assertThat(convertedEntity, equalTo(originalEntity));
    }

    /**
     * This method helps to check that no optional field gets forgotten in the test setup for the data type
     * converter function tests.
     */
    @SneakyThrows
    private void verifyNoFieldHasNullValue(Object object) {
        var objectMapper = new ObjectMapper();
        var objectAsString = objectMapper.writeValueAsString(object);

        assertThat(objectAsString, not(containsString("null")));
    }
}