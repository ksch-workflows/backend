package ksch.bff;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import ksch.testing.TestResource;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@WireMockTest
public class OAuthServiceTest {

    OAuthProperties oauthProperties(WireMockRuntimeInfo wireMockRuntimeInfo) {
        return OAuthProperties.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build();
    }

    String tokenResponseBody() {
        return new TestResource("token-response-body.json").readString();
    }

    @Test
    public void should_exchange_authorization_code_to_token(WireMockRuntimeInfo wireMockRuntimeInfo) {
        // TODO: Stricter request matcher
        stubFor(WireMock.post("/oauth/token").willReturn(ok().withBody(tokenResponseBody())));
        var oauthService = new OAuthService(oauthProperties(wireMockRuntimeInfo));

        var tokenResponse = oauthService.exchangeAuthorizationGrant("kdsf79sdc");

        assertThat(tokenResponse, notNullValue());
    }

    @Test
    public void should_handle_not_found_error_response(WireMockRuntimeInfo wireMockRuntimeInfo) {

    }

    @Test
    public void should_handle_unauthorized_error_response() {

    }

    @Test
    public void should_handle_unexpected_response_body(WireMockRuntimeInfo wireMockRuntimeInfo) {
        stubFor(WireMock.post("/oauth/token").willReturn(ok()));
        var oauthService = new OAuthService(oauthProperties(wireMockRuntimeInfo));
    }
}
