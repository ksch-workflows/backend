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

import ksch.commons.http.ResourceExtensions;
import ksch.commons.http.ResourceExtensionsRegistry;
import ksch.patientmanagement.api.Patient;
import ksch.visit.domain.VisitRepository;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is responsible for the registration of visit-related links in REST resources provided by other modules.
 */
@Component
public class VisitResourceExtensions extends ResourceExtensions {

    private final VisitRepository visitRepository;

    public VisitResourceExtensions(
            ResourceExtensionsRegistry resourceExtensionsRegistry,
            VisitRepository visitRepository
    ) {
        super(resourceExtensionsRegistry);

        this.visitRepository = visitRepository;
    }

    @Override
    public void init() {
        registerLink(Patient.class, this::createStartVisitLink);
        registerLink(Patient.class, this::createCurrentVisitLink);
    }

    private Optional<Link> createStartVisitLink(Patient patient) {
        var currentVisit = visitRepository.findCurrentVisit(patient.getId());
        if (currentVisit.isPresent()) {
            return Optional.empty();
        } else {
            var link = linkTo(
                    methodOn(VisitController.class).startVisit(patient.getId(), null)
            ).withRel("start-visit");
            return Optional.of(link);
        }
    }

    private Optional<Link> createCurrentVisitLink(Patient patient) {
        var currentVisit = visitRepository.findCurrentVisit(patient.getId());
        return currentVisit.map(v -> {
            var link = linkTo(
                    methodOn(VisitController.class).getVisit(patient.getId(), v.getId())
            ).withRel("current-visit");
            return Optional.of(link);
        }).orElse(Optional.empty());
    }
}
