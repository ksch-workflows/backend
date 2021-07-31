package ksch.visit.application;

import ksch.visit.domain.OpdSerialNumber;
import ksch.visit.domain.OpdSerialNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
class OpdNumberGeneratorTest {

    private static final int NUMERIC_VALUE = 1011;

    @InjectMocks
    private OpdNumberGenerator opdNumberGenerator;

    @Mock
    private OpdSerialNumberRepository opdSerialNumberRepository;

    private final int currentYearWithTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;

    @BeforeEach
    public void setup() {
        when(opdSerialNumberRepository.save(any(OpdSerialNumber.class)))
                .thenReturn(new OpdSerialNumber(NUMERIC_VALUE));
    }

    @Test
    public void should_prepend_year() {
        String generatedOpdNumber = opdNumberGenerator.generateOpdNumber();

        assertTrue("Generated OPD number '" + generatedOpdNumber + "' doesn't start with current year",
                generatedOpdNumber.startsWith(currentYearWithTwoDigits + "-"));
    }

    @Test
    public void should_contain_patient_index_number() {
        String generatedPatientNumber = opdNumberGenerator.generateOpdNumber();

        assertTrue("Generated patient number '" + generatedPatientNumber +
                        "' doesn't contain the patient index number '" + NUMERIC_VALUE + "'.",
                generatedPatientNumber.contains("-" + NUMERIC_VALUE));
    }
}
