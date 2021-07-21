package ksch.visit.rest;

import ksch.commons.http.ResourceExtensionsRegistry;
import ksch.patientmanagement.Patient;
import ksch.visit.domain.JohnDoe;
import ksch.visit.infrastructure.VisitJpaRepository;
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
    private VisitJpaRepository visitJpaRepository;

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
