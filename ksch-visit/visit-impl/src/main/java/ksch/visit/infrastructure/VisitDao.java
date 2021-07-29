package ksch.visit.infrastructure;

import ksch.visit.Visit;
import ksch.visit.VisitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VisitDao implements Visit {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true)
    private UUID id;

    private String opdNumber;

    private UUID patientId;

    private VisitType type;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    public static VisitDao from(Visit visit) {
        return VisitDao.builder()
                .id(visit.getId())
                .opdNumber(visit.getOpdNumber())
                .patientId(visit.getPatientId())
                .type(visit.getType())
                .timeStart(visit.getTimeStart())
                .timeEnd(visit.getTimeEnd())
                .build();
    }
}
