package ksch.commons.data;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageAdapter<T> implements Page<T> {

    private final org.springframework.data.domain.Page<T> delegate;

    @Override
    public int getTotalPages() {
        return delegate.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return delegate.getTotalElements();
    }

    @Override
    public int getNumber() {
        return delegate.getNumber();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return delegate.getNumberOfElements();
    }

    @Override
    public List<T> getContent() {
        return delegate.getContent();
    }

    @Override
    public boolean hasContent() {
        return delegate.hasContent();
    }

    @Override
    public Sort getSort() {
        return delegate.getSort();
    }

    @Override
    public boolean isFirst() {
        return delegate.isFirst();
    }

    @Override
    public boolean isLast() {
        return delegate.isLast();
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return delegate.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return delegate.previousPageable();
    }

    @Override
    public <U> org.springframework.data.domain.Page<U> map(Function<? super T, ? extends U> converter) {
        return delegate.map(converter);
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }
}
