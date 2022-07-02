/*
 * Copyright 2021 KS-plus e.V.
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oauthService;

    @GetMapping("/bff/callback")
    Object handleAuthorizationCallback(@RequestParam String code, HttpServletRequest request) {

        var session = request.getSession();
        var interceptedUri = session.getAttribute("interceptedUri");

        // Check that the client followed the intended usage flow
        if (interceptedUri == null) {
            log.warn("It has been attempted to invoke the OAuth callback URL outside of the intended usage flow.");
            return ResponseEntity.status(FORBIDDEN).build();
        }

        // Generate and store OAuth tokens
        var tokenResponse = oauthService.exchangeAuthorizationGrant(code);
        session.setAttribute("accessToken", tokenResponse.getAccessToken());
        session.setAttribute("refreshToken", tokenResponse.getRefreshToken());
        session.setAttribute("interceptedUri", null);

        // Redirect back to the originally intercepted URI
        var headers = new HttpHeaders();
        headers.add("location", String.valueOf(interceptedUri));
        return new ResponseEntity<>(headers, FOUND);
    }
}
