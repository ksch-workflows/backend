package ksch.commons.http.error;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CommonHttpErrorConfigurationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @SneakyThrows
    public void should_fail_get_greeting() {
        var result = mockMvc.perform(get("/commons/http-error-configuration/greeting"))
                .andDo(print());

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorId", is("request-parameter-missing")))
                .andExpect(jsonPath("details.name", is("name")))
                .andExpect(jsonPath("details.type", is("String")))
        ;
    }

    @Test
    @SneakyThrows
    public void should_handle_unknown_error() {
        var result = mockMvc.perform(get("/commons/http-error-configuration/unknown-error"))
                .andDo(print());

        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorId", is("unknown-error")))
                .andExpect(jsonPath("details", nullValue()))
        ;
    }

    @Test
    @SneakyThrows
    public void should_handle_not_found_error() {
        var result = mockMvc.perform(get("/commons/http-error-configuration/not-found-error"))
                .andDo(print());

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("errorId", is("entity-not-found")))
                .andExpect(jsonPath("details", nullValue()))
        ;
    }
}
