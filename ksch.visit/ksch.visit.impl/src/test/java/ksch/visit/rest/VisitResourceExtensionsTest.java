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
import ksch.patientmanagement.api.Patient;
import ksch.visit.Visit;
import ksch.visit.domain.JohnDoe;
import ksch.visit.domain.VisitRepository;
import ksch.visit.infrastructure.VisitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ksch.testing.ResourceExtensionMatchers.containsLinkWithRel;
import static ksch.testing.ResourceExtensionMatchers.containsNoLinkWithRel;
import static ksch.visit.VisitType.IPD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class VisitResourceExtensionsTest {

    private final ResourceExtensionsRegistry resourceExtensionsRegistry = new ResourceExtensionsRegistry();

    @Mock
    private VisitRepository visitJpaRepository;

    private VisitResourceExtensions resourceExtensions;

    @BeforeEach
    public void setup() {
        resourceExtensions = new VisitResourceExtensions(resourceExtensionsRegistry, visitJpaRepository);
    }

    @Test
    public void should_create_start_visit_link() {
        given(visitJpaRepository.findCurrentVisit(any(UUID.class))).willReturn(Optional.empty());
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
        given(visitJpaRepository.findCurrentVisit(any(UUID.class))).willReturn(Optional.of(mock(Visit.class)));
        var patient = new JohnDoe();

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(Patient.class, patient);
        assertThat(links, containsNoLinkWithRel("start-visit"));
    }

    @Test
    public void should_add_current_visit_link() {
        var patient = new JohnDoe();
        given(visitJpaRepository.findCurrentVisit(eq(patient.getId()))).willReturn(
                Optional.of(VisitDao.builder()
                        .id(UUID.randomUUID())
                        .opdNumber("10-12345")
                        .patientId(patient.getId())
                        .timeStart(LocalDateTime.now().minusDays(5))
                        .type(IPD)
                        .build()
                )
        );

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(Patient.class, patient);
        assertThat(links, containsLinkWithRel("current-visit"));
    }

    @Test
    public void should_not_include_past_visit_as_current_visit() {
        var patient = new JohnDoe();
        given(visitJpaRepository.findCurrentVisit(eq(patient.getId()))).willReturn(Optional.empty());

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(Patient.class, patient);
        assertThat(links, containsNoLinkWithRel("current-visit"));
    }
}
