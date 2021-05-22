package ksch.patientmanagement.application;

import ksch.patientmanagement.Patient;
import ksch.patientmanagement.PatientService;
import ksch.patientmanagement.infrastructure.PatientDao;
import ksch.patientmanagement.infrastructure.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientJpaRepository patientRepository;

    public Patient createPatient() {
        return createPatient(new PatientDao());
    }

    public Patient createPatient(Patient patient) {
        return createPatient(PatientDao.from(patient));
    }

    private Patient createPatient(PatientDao patient) {
        return patientRepository.save(patient);
    }
}
