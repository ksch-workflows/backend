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
package ksch.visit;

import ksch.commons.http.ResourceExtensionRegistry;
import ksch.commons.http.ResourceExtensions;
import ksch.patientmanagement.Patient;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VisitResourceExtensions extends ResourceExtensions {

    public VisitResourceExtensions(ResourceExtensionRegistry resourceExtensionRegistry) {
        super(resourceExtensionRegistry);
    }

    @Override
    public void init() {
        registerLink(
                Patient.class,
                (p) -> WebMvcLinkBuilder.linkTo(
                        methodOn(VisitController.class).startVisit(p.getId())
                ).withRel("start-visit")
        );
    }
}
