/*
 * Copyright 2024 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch.commons.data;

import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Converts a {@link Pageable} from Spring Data Common into a {@link PageableFacade} which can be used by the KSCH Core
 * modules without the need of having a direct dependency on Spring Data.
 */
@SuppressWarnings("NullableProblems")
public class PageableAdapter implements PageableFacade {

    private final Pageable delegate;

    public PageableAdapter(Pageable delegate) {
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
    public Pageable next() {
        return delegate.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return delegate.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return delegate.first();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return delegate.withPage(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public Optional<Pageable> toOptional() {
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
