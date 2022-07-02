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

import ksch.bff.util.CustomizedRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class CustomizedRequestTest {

    @Test
    @SneakyThrows
    void should_get_single_header_value() {
        var originalRequest = new MockHttpServletRequest();

        originalRequest.addHeader("X-EXAMPLE-KEY", "example value");
        var customizedRequest = new CustomizedRequest(originalRequest);

        assertThat(customizedRequest.getHeader("X-EXAMPLE-KEY"), equalTo("example value"));
    }

    @Test
    void should_get_header_value_case_insensitive() {
        var request = new CustomizedRequest(new MockHttpServletRequest());

        request.addHeader("X-EXAMPLE-KEY", "example value");

        assertThat(request.getHeader("x-example-key"), equalTo("example value"));
    }

    @Test
    void should_get_multiple_header_values() {
        var request = new CustomizedRequest(new MockHttpServletRequest());
        request.addHeader("X-EXAMPLE-KEY", "example value 1");
        request.addHeader("X-EXAMPLE-KEY", "example value 2");

        var values = toSet(request.getHeaders("X-EXAMPLE-KEY"));

        var expectedValues = new HashSet<String>();
        expectedValues.add("example value 1");
        expectedValues.add("example value 2");
        assertThat(values, equalTo(expectedValues));
    }

    @Test
    void should_not_add_same_value_multiple_times() {
        var request = new CustomizedRequest(new MockHttpServletRequest());
        request.addHeader("X-EXAMPLE-KEY", "example value 1");
        request.addHeader("X-EXAMPLE-KEY", "example value 1");

        var values = toList(request.getHeaders("X-EXAMPLE-KEY"));

        assertThat(values.size(), is(1));
    }

    @Test
    void should_get_empty_header_values() {
        var request = new CustomizedRequest(new MockHttpServletRequest());

        var values = toSet(request.getHeaders("X-EXAMPLE-KEY"));

        assertThat(values.isEmpty(), is(true));
    }

    @Test
    void should_provide_access_on_custom_header() {
        var originalRequest = new MockHttpServletRequest();

        originalRequest.addHeader("X-ORIGINAL-KEY", "original value");
        var customizedRequest = new CustomizedRequest(originalRequest);
        customizedRequest.addHeader("X-CUSTOM-KEY", "custom value");

        assertThat(customizedRequest.getHeader("X-ORIGINAL-KEY"), equalTo("original value"));
        assertThat(customizedRequest.getHeader("X-CUSTOM-KEY"), equalTo("custom value"));
    }

    @Test
    void should_get_header_names() {
        var expectedHeaderNames = new HashSet<String>();
        expectedHeaderNames.add("x-example-1");
        expectedHeaderNames.add("x-example-2");
        var request = requestWithHeaderNames(expectedHeaderNames);

        var headerNames = toSet(request.getHeaderNames());

        assertThat(headerNames, equalTo(expectedHeaderNames));
    }

    HttpServletRequest requestWithHeaderNames(Set<String> headerNames) {
        var result = new CustomizedRequest(new MockHttpServletRequest());
        for (String s : headerNames) {
            result.addHeader(s, UUID.randomUUID().toString());
        }
        return result;
    }

    Set<String> toSet(Enumeration<String> enumeration) {
        var result = new HashSet<String>();
        var it = enumeration.asIterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

    List<String> toList(Enumeration<String> enumeration) {
        var result = new ArrayList<String>();
        var it = enumeration.asIterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }
}
