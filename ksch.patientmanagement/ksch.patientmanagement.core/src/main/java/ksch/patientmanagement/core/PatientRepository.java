package ksch.patientmanagement.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ksch.commons.data.Page;
import ksch.commons.data.Pageable;
import ksch.commons.data.Specification;
import ksch.patientmanagement.api.Patient;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);

    List<Patient> findAll(Pageable pageable);

    Page<Patient> findBySpecification(Specification<Patient> specification, Pageable pageable);
}
