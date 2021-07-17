package ksch.visit;

import ksch.commons.http.ResourceExtensions;
import ksch.patientmanagement.Patient;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class VisitResourceExtensions extends ResourceExtensions {

    @Override
    public void init() {
        registerLink(
                Patient.class,
                (p) -> Link.of("http://localhost/patients/" + p.getId(), "start-visit")
        );
    }
}
