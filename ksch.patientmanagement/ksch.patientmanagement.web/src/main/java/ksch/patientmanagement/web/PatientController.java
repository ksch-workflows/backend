package ksch.patientmanagement.web;

import ksch.patientmanagement.core.PatientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;
}
