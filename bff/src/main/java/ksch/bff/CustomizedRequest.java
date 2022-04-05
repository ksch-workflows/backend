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
