/*
 * Copyright 2021 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @GetMapping("/{patientId}")
    public PatientModel getPatient(@PathVariable("patientId") UUID patientId) {
        var patient = patientRepository.findById(patientId).orElseThrow(NotFoundException::new);
        return patientModelAssembler.toModel(patient);
    }

    @GetMapping
    PagedModel<PatientModel> listPatients(Pageable pageable) {



        var patients = patientRepository.findAll(pageable).map(p -> (Patient) p);
        return pagedResourcesAssembler.toModel(patients, patientModelAssembler);
    }

    @GetMapping("/search")
    PagedModel<PatientModel> searchPatients(@RequestParam("q") String query, Pageable pageable) {
        var patients = patientRepository.findAll(pageable).map(p -> (Patient) p);
        return pagedResourcesAssembler.toModel(patients, patientModelAssembler);
    }

    @GetMapping("/{patientId}/residential-address")
    public HashMap<String, String> getResidentialAddress(@PathVariable("patientId") UUID patientId) {
        var residentialAddress = patientRepository.findById(patientId)
                .orElseThrow(NotFoundException::new)
                .getResidentialAddress();
        var result = new HashMap<String, String>();
        result.put("residentialAddress", residentialAddress);
        return result;
    }
}
