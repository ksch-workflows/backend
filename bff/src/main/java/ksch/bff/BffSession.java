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

import ksch.bff.util.TokenResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class BffSession {

    private final HttpSession session;

    public BffSession(HttpServletRequest request) {
        this.session = request.getSession();
    }

    public BffSession(HttpSession session) {
        this.session = session;
    }

    public static Optional<BffSession> from(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        return Optional.of(new BffSession(session));
    }

    public void setTokens(TokenResponse tokenResponse) {
        session.setAttribute("accessToken", tokenResponse.getAccessToken());
        session.setAttribute("refreshToken", tokenResponse.getRefreshToken());
    }

    public Optional<String> getAccessToken() {
        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {
            return Optional.empty();
        }
        return Optional.of(accessToken.toString());
    }

    public boolean doesNotHaveAccessToken() {
        return getAccessToken().isEmpty();
    }

    public void setInterceptedUri(String uri) {
        session.setAttribute("interceptedUri", uri);
    }

    public void removeInterceptedUri() {
        session.setAttribute("interceptedUri", null);
    }

    public Optional<String> getInterceptedUri() {
        var uri = session.getAttribute("interceptedUri");
        if (uri == null) {
            return Optional.empty();
        }
        return Optional.of(uri.toString());
    }
}
