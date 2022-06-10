package ksch.commons.http.error;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpErrorConfigurationTestController {

    @GetMapping("/commons/http-error-configuration/greeting")
    Object getGreeting(@RequestParam String name) {
        return "Hello " + name;
    }
}
