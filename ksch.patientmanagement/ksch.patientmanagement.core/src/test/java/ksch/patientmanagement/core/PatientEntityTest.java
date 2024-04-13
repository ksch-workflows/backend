package ksch.patientmanagement.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PatientEntityTest {

    @Test
    @DisplayName("Should create patient entity from data interface")
    void test_from_factory_happy_path() {
        var originalPatientEntity = Instancio.create(PatientEntity.class);

        var clonedPatientEntity = PatientEntity.from(originalPatientEntity);

        assertThat(clonedPatientEntity).isEqualTo(originalPatientEntity);
    }
}
