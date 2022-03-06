package ksch.bff;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/bff/callback")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String code) {

        return ResponseEntity.ok().build();
    }
}
