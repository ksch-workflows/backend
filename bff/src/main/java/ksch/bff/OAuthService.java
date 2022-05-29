package ksch.bff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import ksch.commons.http.error.DeserializationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final OAuthProperties oauthProperties;

    public TokenResponse exchangeAuthorizationGrant(String code) {
        var tokenUrl = oauthProperties.getBaseUrl() + "/oauth/token";

        var response = Unirest.post(tokenUrl)
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
            log.error("Failed to deserialize HTTP response", e);
            throw new DeserializationException();
        }
    }
}
