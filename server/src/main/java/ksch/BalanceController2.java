package ksch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController2 {

    @GetMapping("/balance2")
    public String getBalance() {
        return "balance";
    }
}
