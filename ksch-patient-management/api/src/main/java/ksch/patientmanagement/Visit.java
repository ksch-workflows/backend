package ksch.patientmanagement;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Visit {

    UUID getId();

    String getOpdNumber();

    Patient getPatient();

    VisitType getType();

    LocalDateTime getTimeStart();

    LocalDateTime getTimeEnd();
}
