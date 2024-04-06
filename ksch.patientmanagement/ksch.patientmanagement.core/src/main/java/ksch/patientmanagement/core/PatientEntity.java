package ksch.patientmanagement.core;

import java.util.UUID;

import ksch.patientmanagement.api.Gender;
import ksch.patientmanagement.api.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // TODO Can the setters be removed?
// TODO Use builder
public class PatientEntity implements Patient {

    // TODO Use final?
    private UUID id;
    private String name;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private String residentialAddress;
    private String patientCategory;
}
