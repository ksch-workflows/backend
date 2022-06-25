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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
class LoginInterceptor implements HandlerInterceptor {

    private final OAuthProperties props;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isWebPageRequest(request)) {
            return true; // Ignore any requested resources that are not HTML files.
        }
        var session = request.getSession();
        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {

            String interceptedUri;
            if (request.getQueryString() != null) {
                interceptedUri = request.getRequestURI() + "?" + request.getQueryString();
            } else {
                interceptedUri = request.getRequestURI();
            }

            session.setAttribute("interceptedUri", interceptedUri);
            response.setStatus(303);
            response.setHeader("Location", authorizeUrl());
            return false;
        } else {
            return true;
        }
    }

    private String authorizeUrl() {
        return props.getBaseUrl() + "/authorize?response_type=code" +
                "&client_id=" + props.getClientId() +
                "&redirect_uri=" + props.getRedirectUri() +
                "&scope=offline_access%20read%3Apatients" +
                "&audience=https://ksch-workflows.github.io/api";
    }

    private static boolean isWebPageRequest(HttpServletRequest request) {
        var requestURI = request.getRequestURI();
        if (requestURI.endsWith("/")) {
            return true;
        }
        if (requestURI.endsWith(".html")) {
            return true;
        }
        return false;
    }
}
