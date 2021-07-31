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
import ksch.patientmanagement.Patient;
import ksch.visit.domain.JohnDoe;
import ksch.visit.domain.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VisitModuleResourceExtensionsTest {

    private final ResourceExtensionsRegistry resourceExtensionsRegistry = new ResourceExtensionsRegistry();

    @Mock
    private VisitRepository visitJpaRepository;

    private VisitModuleResourceExtensions resourceExtensions;

    @BeforeEach
    public void setup() {
        resourceExtensions = new VisitModuleResourceExtensions(resourceExtensionsRegistry, visitJpaRepository);
    }

    @Test
    public void should_create_start_visit_link() {
        given(visitJpaRepository.hasActiveVisit(any(UUID.class))).willReturn(false);
        var patient = new JohnDoe();

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(Patient.class, patient);
        assertThat(links.size(), is(1));
        assertThat(links.get(0).getRel().value(), is("start-visit"));
        assertThat(links.get(0).getHref(), containsString(patient.getId().toString()));
        assertThat(links.get(0).getHref(), containsString("visits"));
    }

    @Test
    public void should_not_create_start_visit_link() {
        given(visitJpaRepository.hasActiveVisit(any(UUID.class))).willReturn(true);
        var patient = new JohnDoe();

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(Patient.class, patient);
        assertThat(links, empty());
    }
}
