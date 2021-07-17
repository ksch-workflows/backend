package ksch.visit.infrastructure;

import ksch.visit.Visit;
import ksch.visit.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitJpaRepository visitRepository;

    @Override
    public Visit startVisit(UUID patientId) {
        return visitRepository.save(VisitDao.builder()
                .patientId(patientId)
                .timeStart(now())
                .build());
    }
}
