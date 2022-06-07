package ksch.bff;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
// @ContextConfiguration(classes = )
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
                // TODO Is the line below needed?
                .apply(sharedHttpSession()) // use this session across requests
                .build();
    }

    @Test
    @SneakyThrows
    public void should_redirect_to_authorization_server() {
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
    public void should_track_intercepted_uri_in_session() {

    }

    @Test
    @SneakyThrows
    public void should_skip_session_with_access_token() {

    }

    @Test
    @SneakyThrows
    public void should_skip_request_to_api() {

    }

    @Test
    @SneakyThrows
    public void should_skip_request_to_static_resource_file() {

    }

    @Test
    @SneakyThrows
    public void should_skip_request_without_session() {

    }

    @Test
    @SneakyThrows
    public void should_skip_error_page() {

    }

    @RestController
    private class LoginInterceptorTestController {

        @GetMapping("/login-interceptor/test.html")
        Object getHtmlPage() {
            return "<html><body>Hello</body></html>";
        }
    }

    @RequiredArgsConstructor
    class LoginInterceptorVerifier implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            return false;
        }
    }
}
