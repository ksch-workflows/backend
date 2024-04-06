package ksch.patientmanagement.core;

import org.springframework.stereotype.Service;

import ksch.patientmanagement.api.Patient;
import ksch.patientmanagement.api.PatientService;
import lombok.RequiredArgsConstructor;

// TODO Remove the interface
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient createPatient() {
        return patientRepository.save(new PatientEntity());
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
