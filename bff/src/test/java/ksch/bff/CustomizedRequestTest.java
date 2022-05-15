package ksch.bff;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CustomizedRequestTest {

    @Test
    @SneakyThrows
    public void should_provide_access_on_original_header() {
        var originalRequest = new MockHttpServletRequest();

        originalRequest.addHeader("X-EXAMPLE-KEY", "example value");
        var customizedRequest = new CustomizedRequest(originalRequest);

        assertThat(customizedRequest.getHeader("X-EXAMPLE-KEY"), equalTo("example value"));
    }

    @Test
    public void should_provide_access_on_custom_header() {
        var originalRequest = new MockHttpServletRequest();

        originalRequest.addHeader("X-ORIGINAL-KEY", "original value");
        var customizedRequest = new CustomizedRequest(originalRequest);
        customizedRequest.addHeader("X-CUSTOM-KEY", "custom value");

        assertThat(customizedRequest.getHeader("X-ORIGINAL-KEY"), equalTo("original value"));
        assertThat(customizedRequest.getHeader("X-CUSTOM-KEY"), equalTo("custom value"));
    }
}
