package ksch.visit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface VisitJpaRepository extends JpaRepository<VisitDao, UUID>, JpaSpecificationExecutor<VisitDao>
{
    @Query("select count(v) > 0 from VisitDao v where v.patientId = :patientId and v.timeEnd is null")
    boolean hasActiveVisit(@Param("patientId") UUID patientId);
}
