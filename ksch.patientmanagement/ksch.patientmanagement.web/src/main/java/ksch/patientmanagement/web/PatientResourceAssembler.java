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
package ksch.patientmanagement.web;

import ksch.commons.http.ResourceExtensionsRegistry;
import ksch.patientmanagement.api.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
class PatientResourceAssembler implements RepresentationModelAssembler<Patient, PatientResource> {

    private final ResourceExtensionsRegistry resourceExtensionsRegistry;

    @Override
    public PatientResource toModel(Patient patient) {
        var result = PatientResource.from(patient);
        var selfLink = linkTo(PatientController.class).slash(patient.getId()).withSelfRel();
        result.add(selfLink);
        result.add(resourceExtensionsRegistry.getLinks(Patient.class, patient));
        return result;
    }
}
