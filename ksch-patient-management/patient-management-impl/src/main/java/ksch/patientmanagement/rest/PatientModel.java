package ksch.patientmanagement.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;
import ksch.util.TypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
class PatientModel extends RepresentationModel<PatientModel> implements Patient {

    @JsonProperty("_id")
    private UUID id;

    private String patientNumber;

    private String name;

    private Integer age;

    private Gender gender;

    private String phoneNumber;

    private String residentialAddress;

    private String patientCategory;

    static PatientModel from(Patient patient) {
        return new TypeConverter<>(Patient.class).convertTo(patient, PatientModel.class);
    }
}
