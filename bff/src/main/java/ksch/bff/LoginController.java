package ksch.bff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@RestController
public class LoginController {

    private static final String TOKEN_URL = "https://noauth-ga2speboxa-ew.a.run.app/oauth/token";

    @GetMapping("/bff/callback")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String code, HttpServletRequest request) {

        var tokenResponse = exchangeAuthorizationGrant(code);

        var session = request.getSession();
        session.setAttribute("accessToken", tokenResponse.getAccessToken());
        session.setAttribute("refreshToken", tokenResponse.getRefreshToken());

        return ResponseEntity.ok().build();
    }

    public TokenResponse exchangeAuthorizationGrant(String code) {
        var response = Unirest.post(TOKEN_URL)
                .header("content-type", "application/x-www-form-urlencoded")
                .field("client_id", "jnebdD0fczAHoEBVrr6lE7OAuYchc2ZR")
                .field("client_secret", "xxx")
                .field("grant_type", "authorization_code")
                .field("redirect_uri", "http://localhost/callback")
                .field("code", "xxx")
                .asString();

        if (!response.isSuccess()) {
            var msg = String.format("Status code: '%s', response body: '%s'.",
                    response.getStatus(), response.getBody()
            );
            throw new RuntimeException(msg);
        }

        var responseBody = response.getBody();

        var objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(responseBody, TokenResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
