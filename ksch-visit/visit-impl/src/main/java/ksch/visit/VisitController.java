package ksch.visit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/visits")
public class VisitController {

    @GetMapping
    public Object sayHello() {
        return "hello";
    }
}
