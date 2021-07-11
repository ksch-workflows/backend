package ksch.visit;

import ksch.linkregistry.ResourceExtensionRegistry;
import ksch.patientmanagement.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisitResourceExtensions implements ApplicationRunner {

    private final ResourceExtensionRegistry resourceExtensionRegistry;

    @Override
    public void run(ApplicationArguments args) {
        resourceExtensionRegistry.registerLink(
                Patient.class,
                (p) -> Link.of("http://localhost/patients/" + p.getId(), "current-visit")
        );
    }
}
