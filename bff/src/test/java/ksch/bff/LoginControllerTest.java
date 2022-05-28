package ksch.bff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OAuthService oauthService;

    private MockMvc mockMvc;

    TokenResponse tokenResponse() {
        return TokenResponse.builder()
                .accessToken("eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIiwiaXNzIjoiaHR0cHM6Ly9rc2NoLXdvcmtmbG93cy5ldS5hdXRoMC5jb20vIn0..PBydSKHGyPvqQ4PP.t8tUBIzaWiH4ie9m2lNnkVeTn01j-O_j017SqRd5fpY0onl6UpvuY1W-LlABJxMVa-SusyGHPQGoBMsy8iLqkBJePKqupVfWURWQv00IjwLfnm5e5xBiF78k77gz-Jzz7WLDq-eSbWu36ocP3jPUaOqeJaVWJNrdzJMYx9QKEGqvATTlq2xsAL5lDmdiZIfNw3US7xYhc4dsfpXvjAm__72iUX5JaALzKSAmwDA-XQk6EFq8SH2UpWIUYtX__B6xgSDWh7kzZ_FQ_9cFJhi1PjLQWBnpFXt6phPS277ZB_Yfe9no58K-MKOOxKm6-mYDq54SYVE2DR4WnxcSEAWeNwJ_mylt.lhUSic-xYCZA5-asvoJuKA")
                .refreshToken("EnUlvqbeSYWQZjqX41SQX72tyMdxOo3RawtaitqDTbgVP")
                .build();
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(sharedHttpSession()) // use this session across requests
                .build();
    }

    @Test
    public void getAccount() throws Exception {
        when(oauthService.exchangeAuthorizationGrant(any(String.class))).thenReturn(tokenResponse());
        var session = new MockHttpSession();
        session.setAttribute("interceptedUri", "http://intercepted");

        var result = mockMvc.perform(
                get("/bff/callback?code=123klsdf2").session(session)
        );

        result.andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://intercepted"));
    }

    // TODO Test negative result
}
