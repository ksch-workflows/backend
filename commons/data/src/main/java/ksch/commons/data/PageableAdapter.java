package ksch.commons.data;

import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Sort;

public class PageableAdapter implements PageableFacade {

    private final org.springframework.data.domain.Pageable delegate;

    public PageableAdapter(org.springframework.data.domain.Pageable delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isPaged() {
        return delegate.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return delegate.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return delegate.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return delegate.getPageSize();
    }

    @Override
    public long getOffset() {
        return delegate.getOffset();
    }

    @Override
    public Sort getSort() {
        return delegate.getSort();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return delegate.getSortOr(sort);
    }

    @Override
    public org.springframework.data.domain.Pageable next() {
        return delegate.next();
    }

    @Override
    public org.springframework.data.domain.Pageable previousOrFirst() {
        return delegate.previousOrFirst();
    }

    @Override
    public org.springframework.data.domain.Pageable first() {
        return delegate.first();
    }

    @Override
    public org.springframework.data.domain.Pageable withPage(int pageNumber) {
        return delegate.withPage(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public Optional<org.springframework.data.domain.Pageable> toOptional() {
        return delegate.toOptional();
    }

    @Override
    public Limit toLimit() {
        return delegate.toLimit();
    }

    @Override
    public OffsetScrollPosition toScrollPosition() {
        return delegate.toScrollPosition();
    }
}
