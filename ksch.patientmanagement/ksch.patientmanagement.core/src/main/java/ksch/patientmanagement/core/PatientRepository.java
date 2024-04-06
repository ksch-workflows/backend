package ksch.patientmanagement.core;

import java.util.Optional;
import java.util.UUID;

import ksch.commons.data.PageFacade;
import ksch.commons.data.PageableFacade;
import ksch.patientmanagement.api.Patient;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);

    PageFacade<Patient> findAll(PageableFacade pageable);

    PageFacade<Patient> search(String query, PageableFacade pageable);
}
