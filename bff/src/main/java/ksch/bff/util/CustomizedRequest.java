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
package ksch.bff.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomizedRequest extends HttpServletRequestWrapper {
    private final Map<String, List<String>> customHeaders;

    public CustomizedRequest(ServletRequest request) {
        super((HttpServletRequest) request);
        customHeaders = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        final var normalizedName = name.toLowerCase();
        if (customHeaders.containsKey(normalizedName)) {
            var list = customHeaders.get(normalizedName);
            if (!list.contains(value)) {
                list.add(value);
            }
        } else {
            var list = new ArrayList<String>(1);
            list.add(value);
            customHeaders.put(normalizedName, list);
        }
    }

    @Override
    public String getHeader(String name) {
        final var normalizedName = name.toLowerCase();
        if (customHeaders.containsKey(normalizedName)) {
            return customHeaders.get(normalizedName).get(0);
        } else {
            return super.getHeader(normalizedName);
        }
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        var result = Collections.list(super.getHeaderNames());
        result.addAll(customHeaders.keySet());
        return Collections.enumeration(result);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        final var normalizedName = name.toLowerCase();
        var result = Collections.list(super.getHeaders(normalizedName));
        var values = customHeaders.get(normalizedName);
        if (values != null) {
            result.addAll(values);
        }
        return Collections.enumeration(result);
    }
}
