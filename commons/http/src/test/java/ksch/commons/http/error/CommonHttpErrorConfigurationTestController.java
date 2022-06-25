package ksch.commons.http.error;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonHttpErrorConfigurationTestController {

    @GetMapping("/commons/http-error-configuration/greeting")
    Object getGreeting(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("/commons/http-error-configuration/unknown-error")
    Object getUnknownError() {
        throw new RuntimeException("An unknown error occurred.");
    }

    @GetMapping("/commons/http-error-configuration/not-found-error")
    Object getNotFoundError() {
        throw new NotFoundException("xxx");
    }
}
