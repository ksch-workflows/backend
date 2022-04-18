package ksch.bff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/bff/session")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String returnUrl) {
        // The Login interceptor sends the browser to the authorization server, if necessary.
        // This request will only be processed if there is a valid session. Hence, the browser
        // will actually get an initialized session after calling this endpoint, even though there
        // is no business logic in here.
        return ResponseEntity.status(304).header("Location", returnUrl).build();
    }
}
