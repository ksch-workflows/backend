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

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        var req = new CustomizedRequest(request);
        if (isApiRequest(req) && hasSessionCookie(req)) {
            var session = req.getSession(false);
            var accessToken = session.getAttribute("accessToken");
            if (accessToken != null) {
                req.addHeader("Authorization", "Bearer " + accessToken);
            }
        }
        chain.doFilter(req, response);
    }

    private static boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

    private static boolean hasSessionCookie(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("JSESSIONID")) {
                return true;
            }
        }
        return false;
    }
}
