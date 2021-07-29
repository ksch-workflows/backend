package ksch.visit.infrastructure;

import ksch.visit.Visit;
import ksch.visit.domain.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VisitRepositoryImpl implements VisitRepository {

    private final VisitJpaRepository visitJpaRepository;

    @Override
    public boolean hasActiveVisit(UUID patientId) {
        return visitJpaRepository.hasActiveVisit(patientId);
    }

    @Override
    public Visit save(Visit visit) {
        return visitJpaRepository.save(VisitDao.from(visit));
    }
}
