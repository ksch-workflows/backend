package ksch.visit.application;

import ksch.visit.VisitType;
import ksch.visit.domain.VisitCannotBeStartedException;
import ksch.visit.domain.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @InjectMocks
    VisitServiceImpl visitService;

    @Mock
    VisitRepository visitRepository;

    @Test
    public void should_reject_to_start_visit_if_there_is_already_an_active_visit() {
        when(visitRepository.hasActiveVisit(any(UUID.class))).thenReturn(true);

        assertThrows(
                VisitCannotBeStartedException.class,
                () -> visitService.startVisit(UUID.randomUUID(), VisitType.IPD)
        );
    }
}
