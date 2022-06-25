package ksch.bff;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffHttpErrorConfigurationTestController {

    @GetMapping("/bff/http-error-configuration/oauth-failure")
    Object getOAuthFailure() {
        throw new OAuthException();
    }
}
