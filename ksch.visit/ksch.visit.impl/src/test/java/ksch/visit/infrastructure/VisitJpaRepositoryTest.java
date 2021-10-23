package ksch.visit.infrastructure;

import ksch.visit.VisitType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@Transactional
public class VisitJpaRepositoryTest {

    @Autowired
    private VisitJpaRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void should_find_current_visit() {
        var patientId = UUID.randomUUID();
        repository.save(VisitDao.builder()
                .type(VisitType.IPD)
                .timeStart(LocalDateTime.now().minusDays(5))
                .patientId(patientId)
                .build());

        var currentVisit = repository.findCurrentVisit(patientId);

        assertThat(currentVisit.isPresent(), is(true));
    }

    @Test
    public void should_not_find_past_visit_as_current_visit() {
        var patientId = UUID.randomUUID();
        repository.save(VisitDao.builder()
                .type(VisitType.IPD)
                .timeStart(LocalDateTime.now().minusDays(5))
                .timeEnd(LocalDateTime.now().minusDays(3))
                .patientId(patientId)
                .build());

        var currentVisit = repository.findCurrentVisit(patientId);

        assertThat(currentVisit.isPresent(), is(false));
    }
}
