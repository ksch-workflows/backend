package ksch.visit.rest;

import ksch.visit.VisitType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static ksch.testing.ObjectVerifier.verifyAllFieldsAreSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class VisitResourceTest {

    @Test
    public void should_create_object_from_interface() {
        var originalObject = VisitResource.builder()
                .id(UUID.randomUUID())
                .opdNumber("10-1234")
                .patientId(UUID.randomUUID())
                .type(VisitType.IPD)
                .timeStart(now().minus(1, DAYS))
                .timeEnd(now())
                .build();
        verifyAllFieldsAreSet(originalObject);

        var convertedObject = VisitResource.from(originalObject);

        assertThat(convertedObject, equalTo(originalObject));
    }
}
