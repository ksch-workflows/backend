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

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
public class LoginController {

    private final OAuthService oAuthService;

    public LoginController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @GetMapping("/bff/callback")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String code, HttpServletRequest request) {

        var s = session();
        var u = s.getAttribute("interceptedUri");


        var tokenResponse = oAuthService.exchangeAuthorizationGrant(code);

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
}
