package ksch.patientmanagement.patient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Component
public interface PatientRepository extends CrudRepository<PatientEntity, UUID> {

    PatientEntity getById(UUID patientId);
}
