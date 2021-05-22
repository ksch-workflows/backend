package ksch.patientmanagement.rest;

import ksch.patientmanagement.Patient;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
class PatientModelAssembler implements RepresentationModelAssembler<Patient, PatientModel> {

    @Override
    public PatientModel toModel(Patient patient) {
        var result = PatientModel.from(patient);
        var selfLink = linkTo(PatientController.class).slash(patient.getId()).withSelfRel();
        result.add(selfLink);
        return result;
    }
}