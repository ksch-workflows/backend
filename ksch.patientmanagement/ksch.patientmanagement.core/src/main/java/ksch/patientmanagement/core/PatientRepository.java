package ksch.patientmanagement.core;

import java.util.Optional;
import java.util.UUID;

import ksch.patientmanagement.api.Patient;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);
}
