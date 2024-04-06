package ksch.patientmanagement.core;

import java.util.UUID;

import ksch.patientmanagement.api.Gender;
import ksch.patientmanagement.api.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PatientEntity implements Patient {
    private final UUID id;
    private final String name;
    private final Integer age;
    private final Gender gender;
    private final String phoneNumber;
    private final String residentialAddress;
    private final String patientCategory;
}
