package ksch.patientmanagement.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
class PatientPayload implements Patient {

    @JsonProperty("_id")
    private UUID id;

    private String patientNumber;

    private String name;

    private Integer age;

    private Gender gender;

    private String phoneNumber;

    private String residentialAddress;

    private String patientCategory;
}
