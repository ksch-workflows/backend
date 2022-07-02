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

import ksch.bff.domain.OAuthService;
import ksch.bff.util.TokenResponse;
import ksch.commons.http.error.DeserializationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OAuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OAuthService oauthService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @SneakyThrows
    void should_create_redirection(){
        when(oauthService.exchangeAuthorizationGrant(any(String.class))).thenReturn(tokenResponse());
        var session = new MockHttpSession();
        session.setAttribute("interceptedUri", "http://intercepted");

        var result = mockMvc.perform(
                get("/bff/callback?code=123klsdf2").session(session)
        );

        result.andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://intercepted"));
    }

    @Test
    @SneakyThrows
    void should_respond_with_server_error_on_deserialization_failure() {
        when(oauthService.exchangeAuthorizationGrant(any(String.class))).thenThrow(new DeserializationException());
        var session = new MockHttpSession();
        session.setAttribute("interceptedUri", "http://intercepted");

        var result = mockMvc.perform(
                get("/bff/callback?code=123klsdf2").session(session)
        ).andDo(print());

        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorId", is("deserialization-error")));
    }

    @Test
    @SneakyThrows
    void should_forbid_token_generation_without_intercepted_uri() {
        var result = mockMvc.perform(
                get("/bff/callback?code=123klsdf2")
        ).andDo(print());

        result.andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void should_reset_intercepted_uri_after_redirect() {
        when(oauthService.exchangeAuthorizationGrant(any(String.class))).thenReturn(tokenResponse());
        var session = new MockHttpSession();
        session.setAttribute("interceptedUri", "http://intercepted");

        mockMvc.perform(
                get("/bff/callback?code=123klsdf2").session(session)
        );

        assertThat(session.getAttribute("interceptedUri"), nullValue());
    }

    TokenResponse tokenResponse() {
        return TokenResponse.builder()
                .accessToken("eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIiwiaXNzIjoiaHR0cHM6Ly9rc2NoLXdvcmtmbG93cy5ldS5hdXRoMC5jb20vIn0..PBydSKHGyPvqQ4PP.t8tUBIzaWiH4ie9m2lNnkVeTn01j-O_j017SqRd5fpY0onl6UpvuY1W-LlABJxMVa-SusyGHPQGoBMsy8iLqkBJePKqupVfWURWQv00IjwLfnm5e5xBiF78k77gz-Jzz7WLDq-eSbWu36ocP3jPUaOqeJaVWJNrdzJMYx9QKEGqvATTlq2xsAL5lDmdiZIfNw3US7xYhc4dsfpXvjAm__72iUX5JaALzKSAmwDA-XQk6EFq8SH2UpWIUYtX__B6xgSDWh7kzZ_FQ_9cFJhi1PjLQWBnpFXt6phPS277ZB_Yfe9no58K-MKOOxKm6-mYDq54SYVE2DR4WnxcSEAWeNwJ_mylt.lhUSic-xYCZA5-asvoJuKA")
                .refreshToken("EnUlvqbeSYWQZjqX41SQX72tyMdxOo3RawtaitqDTbgVP")
                .build();
    }
}
