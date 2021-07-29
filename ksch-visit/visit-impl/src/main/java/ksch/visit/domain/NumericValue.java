package ksch.visit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * Utility class for generation of the numeric part of new OPD numbers.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TableGenerator(name = "numeric_part_of_opd_number", initialValue = 999)
public class NumericValue {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "numeric_part_of_opd_number")
    @Column(unique = true)
    private int value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
