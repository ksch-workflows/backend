package ksch.patientmanagement.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import ksch.commons.data.Page;
import ksch.commons.data.PageAdapter;
import ksch.commons.data.Pageable;
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
    public Page<Patient> findAll(Pageable pageable) {
        return new PageAdapter<>(patientJpaRepository.findAll(pageable).map(p -> p));
    }

    @Override
    public Page<Patient> search(String query, Pageable pageable) {
        return new PageAdapter<>(patientJpaRepository
            .findAll(new PatientSearchSpecification(query), pageable).map(p -> p));
    }
}
