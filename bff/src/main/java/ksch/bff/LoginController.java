package ksch.bff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
public class LoginController {

    private static final String TOKEN_URL = "https://noauth-ga2speboxa-ew.a.run.app/oauth/token";

    @GetMapping("/bff/callback")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String code, HttpServletRequest request) {

        var tokenResponse = exchangeAuthorizationGrant(code);

        var session = request.getSession();
        session.setAttribute("accessToken", tokenResponse.getAccessToken());
        session.setAttribute("refreshToken", tokenResponse.getRefreshToken());

        var interceptedUri = session.getAttribute("interceptedUri");
        if (interceptedUri != null) {
            var headers = new HttpHeaders();
            headers.add("location", String.valueOf(interceptedUri));
            return new ResponseEntity<>(headers, FOUND);
        }

        return ResponseEntity.ok().build();
    }

    // TODO Make service class for dealing with the HTTP request
    public TokenResponse exchangeAuthorizationGrant(String code) {
        var response = Unirest.post(TOKEN_URL)
                .header("content-type", "application/x-www-form-urlencoded")
                .field("client_id", "jnebdD0fczAHoEBVrr6lE7OAuYchc2ZR") // TODO Use client ID from env variable
                .field("client_secret", "xxx") // TODO Use client secret from env variable
                .field("grant_type", "authorization_code")
                .field("redirect_uri", "http://localhost/callback")
                .field("code", code)
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
