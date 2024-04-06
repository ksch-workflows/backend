package ksch.patientmanagement.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import ksch.commons.data.PageFacade;
import ksch.commons.data.PageAdapter;
import ksch.commons.data.PageableFacade;
import ksch.patientmanagement.api.Patient;
import ksch.patientmanagement.core.PatientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    @Override
    public Patient save(Patient patient) {
        return patientJpaRepository.save(PatientDao.from(patient));
    }

    @Override
    public Optional<Patient> findById(UUID patientId) {
        return patientJpaRepository.findById(patientId).map(p -> p);
    }

    @Override
    public PageFacade<Patient> findAll(PageableFacade pageable) {
        return new PageAdapter<>(patientJpaRepository.findAll(pageable).map(p -> p));
    }

    @Override
    public PageFacade<Patient> search(String query, PageableFacade pageable) {
        return new PageAdapter<>(patientJpaRepository
            .findAll(new PatientSearchSpecification(query), pageable).map(p -> p));
    }
}
