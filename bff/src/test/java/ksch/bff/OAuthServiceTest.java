/*
 * Copyright 2022 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch.bff;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import ksch.bff.config.OAuthProperties;
import ksch.bff.domain.OAuthService;
import ksch.bff.util.OAuthException;
import ksch.commons.http.error.DeserializationException;
import ksch.testing.TestResource;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest
class OAuthServiceTest {

    OAuthProperties oauthProperties(WireMockRuntimeInfo wireMockRuntimeInfo) {
        return OAuthProperties.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .clientId("abcdefg")
                .clientSecret("xxxxxxx")
                .redirectUri("http://localhost/redirect")
                .build();
    }

    String tokenResponseBody() {
        return new TestResource("token-response-body.json").readString();
    }

    String unexpectedTokenResponseBody() {
        return new TestResource("unexpected-token-response-body.json").readString();
    }

    @Test
    void should_exchange_authorization_code_to_token(WireMockRuntimeInfo wireMockRuntimeInfo) {
        stubFor(WireMock.post("/oauth/token").willReturn(ok().withBody(tokenResponseBody())));
        var oauthService = new OAuthService(oauthProperties(wireMockRuntimeInfo));

        var tokenResponse = oauthService.exchangeAuthorizationGrant("kdsf79sdc");

        assertThat(tokenResponse, notNullValue());
    }

    @Test
    void should_handle_not_found_error_response(WireMockRuntimeInfo wireMockRuntimeInfo) {
        stubFor(WireMock.post("/oauth/token").willReturn(badRequest()));
        var oauthService = new OAuthService(oauthProperties(wireMockRuntimeInfo));

        assertThrows(OAuthException.class, () -> {
            oauthService.exchangeAuthorizationGrant("kdsf79sdc");
        });
    }

    @Test
    void should_handle_unexpected_response_body(WireMockRuntimeInfo wireMockRuntimeInfo) {
        stubFor(WireMock.post("/oauth/token").willReturn(ok().withBody(unexpectedTokenResponseBody())));
        var oauthService = new OAuthService(oauthProperties(wireMockRuntimeInfo));

        assertThrows(DeserializationException.class, () -> {
            oauthService.exchangeAuthorizationGrant("kdsf79sdc");
        });
    }
}
