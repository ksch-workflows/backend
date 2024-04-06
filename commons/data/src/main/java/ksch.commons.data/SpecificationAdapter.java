package ksch.commons.data;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@SuppressWarnings({"rawtypes", "NullableProblems", "unchecked"})
@RequiredArgsConstructor
public class SpecificationAdapter implements Specification {

    private final org.springframework.data.jpa.domain.Specification delegate;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return delegate.toPredicate(root, query, criteriaBuilder);
    }

    @Override
    public org.springframework.data.jpa.domain.Specification and(org.springframework.data.jpa.domain.Specification other) {
        return delegate.and(other);
    }

    @Override
    public org.springframework.data.jpa.domain.Specification or(org.springframework.data.jpa.domain.Specification other) {
        return delegate.or(other);
    }
}
