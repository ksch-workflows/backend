package ksch.visit;

import ksch.linkregistry.ResourceExtensions;
import ksch.patientmanagement.Patient;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class VisitResourceExtensions extends ResourceExtensions {

    @Override
    public void init() {
        registerLink(
                Patient.class,
                (p) -> Link.of("http://localhost/patients/" + p.getId(), "current-visit")
        );
    }
}
