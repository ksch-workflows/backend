package ksch.visit.domain;

import ksch.visit.Visit;

import java.util.UUID;

public interface VisitRepository {

    boolean hasActiveVisit(UUID patientId);

    Visit save(Visit visit);
}
