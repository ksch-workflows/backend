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

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TokenFilterTest {

    private MockMvc mockMvc;

    private VerificationFilter verificationFilter;

    @BeforeEach
    public void setup() {
        verificationFilter = new VerificationFilter();
        mockMvc = MockMvcBuilders.standaloneSetup(new TokenFilterTestController())
                .addFilter(new TokenFilter())
                .addFilter(verificationFilter)
                .build();
    }

    @Test
    @SneakyThrows
    void should_filter_api_request_with_session_cookie() {
        var session = new MockHttpSession();
        session.setAttribute("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA3Mjk5MjYsImV4cCI6MTY1MDgxNjMyNiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.HGQfzXCi278UImFOjZn_vdxAflti-OkycjTTXA5RS9Y");
        var sessionCookie = new MockCookie("JSESSIONID", "123423234");

        mockMvc.perform(get("/api/test").session(session).cookie(sessionCookie));

        assertThat(verificationFilter.authorizationHeader, startsWith("Bearer ey"));
    }

    @Test
    @SneakyThrows
    void should_skip_non_api_request() {
        var session = new MockHttpSession();
        session.setAttribute("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA3Mjk5MjYsImV4cCI6MTY1MDgxNjMyNiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.HGQfzXCi278UImFOjZn_vdxAflti-OkycjTTXA5RS9Y");
        var sessionCookie = new MockCookie("JSESSIONID", "123423234");

        mockMvc.perform(get("/bff/test").session(session).cookie(sessionCookie));

        assertThat(verificationFilter.authorizationHeader, nullValue());
    }

    @Test
    @SneakyThrows
    void should_skip_request_without_any_cookie() {
        var session = new MockHttpSession();
        session.setAttribute("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA3Mjk5MjYsImV4cCI6MTY1MDgxNjMyNiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.HGQfzXCi278UImFOjZn_vdxAflti-OkycjTTXA5RS9Y");

        mockMvc.perform(get("/api/test").session(session));

        assertThat(verificationFilter.authorizationHeader, nullValue());
    }

    @Test
    @SneakyThrows
    void should_skip_request_without_session_cookie() {
        var session = new MockHttpSession();
        session.setAttribute("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA3Mjk5MjYsImV4cCI6MTY1MDgxNjMyNiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.HGQfzXCi278UImFOjZn_vdxAflti-OkycjTTXA5RS9Y");
        var miscellaneousCookie = new MockCookie("misc", "abcdefg");

        mockMvc.perform(get("/api/test").session(session).cookie(miscellaneousCookie));

        assertThat(verificationFilter.authorizationHeader, nullValue());
    }

    @Test
    @SneakyThrows
    void should_skip_session_without_access_token() {
        var session = new MockHttpSession();
        var sessionCookie = new MockCookie("JSESSIONID", "123423234");

        mockMvc.perform(get("/api/test").session(session).cookie(sessionCookie));

        assertThat(verificationFilter.authorizationHeader, nullValue());
    }

    @RestController
    private static class TokenFilterTestController {

        @GetMapping("/api/test")
        Object test() {
            return "Hello";
        }

        @GetMapping("/bff/test")
        Object bffTest() {
            return "Hello";
        }
    }

    @Getter
    private static class VerificationFilter implements Filter {

        private String authorizationHeader;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
            var req = (HttpServletRequest) request;
            authorizationHeader = req.getHeader("Authorization");
        }
    }
}
