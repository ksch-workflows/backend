package ksch.patientmanagement.core;

import java.util.Optional;
import java.util.UUID;

import ksch.commons.data.Page;
import ksch.commons.data.Pageable;
import ksch.patientmanagement.api.Patient;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);

    Page<Patient> findAll(Pageable pageable);

    Page<Patient> search(String query, Pageable pageable);
}
