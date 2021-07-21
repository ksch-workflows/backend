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
package ksch.visit.rest;

import ksch.commons.http.ResourceExtensionsRegistry;
import ksch.commons.http.ResourceExtensions;
import ksch.patientmanagement.Patient;
import ksch.visit.infrastructure.VisitJpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is responsible to register the visit related links in related REST resources.
 */
@Component
public class VisitModuleResourceExtensions extends ResourceExtensions {

    private final VisitJpaRepository visitRepository;

    public VisitModuleResourceExtensions(
            ResourceExtensionsRegistry resourceExtensionsRegistry,
            VisitJpaRepository visitRepository
    ) {
        super(resourceExtensionsRegistry);

        this.visitRepository = visitRepository;
    }

    @Override
    public void init() {
        registerLink(Patient.class, patient -> createStartVisitLink(patient));
    }

    private Optional<Link> createStartVisitLink(Patient patient) {
        if (visitRepository.hasActiveVisit(patient.getId())) {
            return Optional.empty();
        } else {
            var link = linkTo(methodOn(VisitController.class).startVisit(patient.getId())).withRel("start-visit");
            return Optional.of(link);
        }
    }
}
