package ksch.visit.infrastructure;

import ksch.visit.domain.NumericValue;
import ksch.visit.domain.OpdNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpdNumberRepositoryImpl implements OpdNumberRepository {

    private final OpdNumberJpaRepository opdNumberJpaRepository;

    @Override
    public NumericValue save(NumericValue numericValue) {
        return opdNumberJpaRepository.save(numericValue);
    }
}
