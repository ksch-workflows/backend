package ksch.patientmanagement.core;

import org.springframework.stereotype.Service;

import ksch.patientmanagement.api.Patient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient createPatient() {
        return patientRepository.save(new PatientEntity());
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
