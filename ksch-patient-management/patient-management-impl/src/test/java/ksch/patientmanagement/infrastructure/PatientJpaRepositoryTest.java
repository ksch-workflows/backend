package ksch.patientmanagement.infrastructure;

import ksch.patientmanagement.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@Transactional
public class PatientJpaRepositoryTest {

    @Autowired
    private PatientJpaRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @BeforeEach
    public void createPatients() {
        patientService.createPatient(new JaneDoe());
        patientService.createPatient(new JohnDoe());
    }

    @Test
    public void should_find_patients_by_last_name() {
        var result = patientRepository.findAll(new PatientSearchSpecification("Doe"));
        assertEquals(2, result.size());
    }

    @Test
    public void should_find_no_results_for_unknown_patient_name() {
        var result = patientRepository.findAll(new PatientSearchSpecification("Max Mustermann"));
        assertEquals(0, result.size());
    }

    @Test
    public void should_find_patient_by_full_name() {
        var result = patientRepository.findAll(new PatientSearchSpecification("John Doe"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_full_name_case_insensitive() {
        var result = patientRepository.findAll(new PatientSearchSpecification("john doe"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_partial_name() {
        var result = patientRepository.findAll(new PatientSearchSpecification("john"));
        assertEquals(1, result.size());

        var firstPatient = result.get(0);
        assertEquals("John Doe", firstPatient.getName());
    }

    @Test
    public void should_find_patient_by_id() {

    }

    @Test
    public void should_find_patient_by_opd_number() {

    }
}
