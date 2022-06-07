package ksch.bff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

// TODO Use token url from profile configuration
@Component
@ConfigurationProperties(prefix = "oauth")
@NoArgsConstructor // TODO Is this really needed?
@Setter // TODO Is this really needed?
@AllArgsConstructor
@Builder
public class OAuthProperties {

    private static final String DEFAULT_TOKEN_URL = "https://noauth-ga2speboxa-ew.a.run.app";

    private String baseUrl;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    /**
     * @return protocol, hostname, and domain of the authorization server.
     */
    public String getBaseUrl() {
        return Objects.requireNonNullElse(baseUrl, DEFAULT_TOKEN_URL);
    }

    public String getClientId() {
        return Objects.requireNonNull(clientId);
    }

    public String getClientSecret() {
        return Objects.requireNonNull(clientSecret);
    }

    public String getRedirectUri() {
        return Objects.requireNonNull(redirectUri);
    }
}
