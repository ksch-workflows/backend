package ksch.visit.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

// TODO Unit test for OPD number generator
@Service
@RequiredArgsConstructor
public class OpdNumberGenerator {

    private static final int currentYearWithTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;

    private final OpdNumberRepository opdNumberRepository;

    public synchronized String generateOpdNumber() {
        NumericValue numericValue = opdNumberRepository.save(new NumericValue());
        return String.format("%s-%s", currentYearWithTwoDigits, numericValue);
    }
}
