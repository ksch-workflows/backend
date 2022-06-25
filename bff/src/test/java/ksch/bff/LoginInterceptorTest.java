package ksch.bff;

import lombok.SneakyThrows;
import org.hamcrest.Matchers;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LoginInterceptorTest {

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
    public void should_redirect_html_page_to_authorization_server() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/login-interceptor/test.html").session(session))
                .andDo(print());

        result.andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", Matchers.startsWith("http://authorization-server")))
                .andExpect(header().string("location", containsString("client_id=example_client_id")))
                .andExpect(header().string("location", containsString("redirect_uri=http://localhost/redirect")))
        ;
    }

    @Test
    @SneakyThrows
    public void should_redirect_trailing_slash_request_to_authorization_server() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/login-interceptor/example/").session(session))
                .andDo(print());

        result.andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", Matchers.startsWith("http://authorization-server")))
                .andExpect(header().string("location", containsString("client_id=example_client_id")))
                .andExpect(header().string("location", containsString("redirect_uri=http://localhost/redirect")))
        ;
    }

    @Test
    @SneakyThrows
    public void should_track_intercepted_uri_in_session() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/login-interceptor/test.html").session(session))
                .andDo(print());

        result.andExpect(status().is3xxRedirection());
        assertThat(session.getAttribute("interceptedUri"), equalTo("/login-interceptor/test.html"));
    }

    @Test
    @SneakyThrows
    public void should_track_intercepted_uri_with_query_params_in_session() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/login-interceptor/test.html?key=value").session(session))
                .andDo(print());

        result.andExpect(status().is3xxRedirection());
        assertThat(session.getAttribute("interceptedUri"), equalTo("/login-interceptor/test.html?key=value"));
    }

    @Test
    @SneakyThrows
    public void should_skip_session_with_access_token() {
        var session = new MockHttpSession();
        session.setAttribute("accessToken", "eyXXXXXXXX");

        var result = mockMvc.perform(get("/login-interceptor/test.html").session(session))
                .andDo(print());

        result.andExpect(status().isOk());
        assertThat(session.getAttribute("interceptedUri"), nullValue());
    }

    @Test
    @SneakyThrows
    public void should_skip_request_to_api() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/api/greeting").session(session))
                .andDo(print());

        result.andExpect(status().isOk());
        assertThat(session.getAttribute("interceptedUri"), nullValue());
    }

    @Test
    @SneakyThrows
    public void should_skip_request_to_static_resource_file() {
        var session = new MockHttpSession();

        var result = mockMvc.perform(get("/login-interceptor/logo.png").session(session))
                .andDo(print());

        result.andExpect(status().isOk());
        assertThat(session.getAttribute("interceptedUri"), nullValue());
    }
}
