package ksch.patientmanagement.rest;

import ksch.commons.http.NotFoundException;
import ksch.patientmanagement.Patient;
import ksch.patientmanagement.PatientService;
import ksch.patientmanagement.infrastructure.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
class PatientController {

    private final PatientService patientService;

    private final PatientJpaRepository patientRepository;

    private final PagedResourcesAssembler<Patient> pagedResourcesAssembler;

    private final PatientModelAssembler patientModelAssembler;

    @PostMapping
    PatientModel createPatient(@RequestBody Optional<PatientPayload> request) {
        Patient patient = null;
        if (request.isPresent()) {
            patient = patientService.createPatient(request.get());
        } else {
            patient = patientService.createPatient();
        }
        return patientModelAssembler.toModel(patient);
    }

    @GetMapping
    PagedModel<PatientModel> listPatients(Pageable pageable) {
        var patients = patientRepository.findAll(pageable).map(p -> (Patient) p);
        return pagedResourcesAssembler.toModel(patients, patientModelAssembler);
    }

    @GetMapping("/{patientId}")
    public PatientModel getPatient(@PathVariable("patientId") UUID patientId) {
        var patient = patientRepository.findById(patientId).orElseThrow(NotFoundException::new);
        return patientModelAssembler.toModel(patient);
    }

    @GetMapping("/{patientId}/residential-address")
    public String getResidentialAddress(@PathVariable("patientId") UUID patientId) {
        return patientRepository.findById(patientId).orElseThrow(NotFoundException::new).getResidentialAddress();
    }
}
