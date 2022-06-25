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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final OAuthProperties oauthProperties;

    public TokenResponse exchangeAuthorizationGrant(String code) {
        var tokenUrl = oauthProperties.getBaseUrl() + "/oauth/token";

        var response = Unirest.post(tokenUrl)
                .header("content-type", "application/x-www-form-urlencoded")
                .field("client_id", oauthProperties.getClientId())
                .field("client_secret", oauthProperties.getClientSecret())
                .field("grant_type", "authorization_code")
                .field("redirect_uri", oauthProperties.getRedirectUri())
                .field("code", code)
                .asString();

        if (!response.isSuccess()) {
            log.warn("Received status code '{}' and response body '{}' for authorization code exchange.",
                    response.getStatus(), response.getBody()
            );
            throw new OAuthException();
        }

        try {
            return objectMapper.readValue(response.getBody(), TokenResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize HTTP response", e);
            throw new DeserializationException();
        }
    }
}
