package ksch.visit.application;

import ksch.visit.Visit;
import ksch.visit.VisitService;
import ksch.visit.VisitType;
import ksch.visit.domain.VisitCannotBeStartedException;
import ksch.visit.domain.VisitRepository;
import ksch.visit.infrastructure.VisitDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public Visit startVisit(UUID patientId, VisitType type) {
        // TODO Check that patient actually exists

        if (visitRepository.hasActiveVisit(patientId)) {
            var message = "Visit cannot be started because there is already an active visit for patient with ID '" +
                    patientId + "'.";
            throw new VisitCannotBeStartedException(message);
        }
        // TODO Generate OPD number
        return visitRepository.save(VisitDao.builder()
                .patientId(patientId)
                .type(type)
                .timeStart(now())
                .build());
    }
}
