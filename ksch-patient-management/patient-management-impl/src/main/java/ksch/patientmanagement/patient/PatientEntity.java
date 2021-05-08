package ksch.patientmanagement.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true)
    private UUID id;

    @Column
    private String name;

    @Column
    private String nameFather;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String gender;

    @Column
    private String address;
}