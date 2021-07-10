package ksch.billing;

import ksch.linkregistry.LinkRegistry;
import ksch.patientmanagement.Patient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class PatientResourceExtensions {

    private final LinkRegistry linkRegistry;

    public PatientResourceExtensions(LinkRegistry linkRegistry) {
        this.linkRegistry = linkRegistry;
    }

    public void run(ApplicationArguments args) {
        linkRegistry.registerLink(
                Patient.class,
                "open-bills",
                (p) -> new Link("http://localhost/patients/" + p.getId(), "open-bills")
        );
    }
}
