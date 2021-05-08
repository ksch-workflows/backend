package ksch.patientmanagement.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @PostMapping("/api/patients")
    public PatientEntity createPatient() {
        return patientRepository.save(PatientEntity.builder()
                .name("John Doe")
                .gender("MALE")
                .address("Guesthouse")
                .dateOfBirth(LocalDate.now().minusYears(25))
                .build());
    }

    @GetMapping("/api/patients")
    public Iterable<PatientEntity> getPatient() {
        return patientRepository.findAll();
    }
}
