package ksch.bff;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginInterceptorTestController {

    @GetMapping("/login-interceptor/test.html")
    Object getHtmlPage() {
        return "<html><body>Hello</body></html>";
    }

    @GetMapping("/api/greeting")
    Object getGreeting() {
        return "Hello";
    }

    @GetMapping("/login-interceptor/logo.png")
    Object getStaticResource() {
        return "00100011110000111100000000011111111110001101000111010101";
    }
}
