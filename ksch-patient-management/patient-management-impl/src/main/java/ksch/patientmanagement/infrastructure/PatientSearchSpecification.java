package ksch.patientmanagement.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class PatientSearchSpecification implements Specification<PatientDao> {

    final String searchCriteria;

    @Override
    public Predicate toPredicate(Root<PatientDao> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.or(
                builder.like(
                        builder.lower(root.get("name")),
                        "%" + searchCriteria.toLowerCase() + "%"
                )
        );
    }
}
