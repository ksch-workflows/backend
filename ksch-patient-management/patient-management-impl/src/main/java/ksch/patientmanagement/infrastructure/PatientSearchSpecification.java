package ksch.patientmanagement.infrastructure;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Spring Data JPA Specification for patient search
 *
 * @see <a href="https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/">
 *     spring.io</a>
 * @see <a href="https://www.baeldung.com/rest-api-search-language-spring-data-specifications">
 *     baeldung.com</a>
 * @see <a href="https://reflectoring.io/spring-data-specifications/#writing-queries-with-specifications">
 *     reflectoring.io</a>
 * @see <a href="https://stackoverflow.com/questions/4580285/jpa2-case-insensitive-like-matching-anywhere">
 *     stackoverflow.com</a>
 * @see <a href="https://www.code4copy.com/java/validate-uuid-string-java/">
 *     code4copy.com</a>
 */
public class PatientSearchSpecification implements Specification<PatientDao> {

    private final static Pattern UUID_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");


    final String searchCriteria;

    public PatientSearchSpecification(String searchCriteria) {
        this.searchCriteria = searchCriteria.trim().toLowerCase();
    }

    @Override
    public Predicate toPredicate(Root<PatientDao> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (isUuid(searchCriteria)) {
            return builder.equal(root.get("id"), UUID.fromString(searchCriteria));
        }

        return builder.or(
                builder.equal(
                        builder.lower(root.get("patientNumber")), searchCriteria
                ),
                builder.like(
                        builder.lower(root.get("name")),
                        "%" + searchCriteria + "%"
                )
        );
    }

    public static boolean isUuid(String string) {
        if (string == null) {
            return false;
        }
        return UUID_PATTERN.matcher(string).matches();
    }
}
