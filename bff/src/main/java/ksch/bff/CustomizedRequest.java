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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

class CustomizedRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public CustomizedRequest(ServletRequest request) {
        super((HttpServletRequest) request);
        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (customHeaders.containsKey(name)) {
            return customHeaders.get(name);
        } else {
            return super.getHeader(name);
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
        var result = Collections.list(super.getHeaders(name));
        result.add(customHeaders.get(name));
        return Collections.enumeration(result);
    }
}
