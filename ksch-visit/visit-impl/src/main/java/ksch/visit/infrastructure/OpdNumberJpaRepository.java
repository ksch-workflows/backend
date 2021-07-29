package ksch.visit.infrastructure;

import ksch.visit.domain.NumericValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OpdNumberJpaRepository extends CrudRepository<NumericValue, Integer> {
}