package ksch.bff;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isWebPageRequest(request) && !isSessionRequest(request)) {
            return true; // Ignore any requested resources that are not HTML files.
            // TODO Also skip error pages
        }
        var session = request.getSession();
        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {

            String interceptedUri;
            if (request.getQueryString() != null) {
                interceptedUri = request.getRequestURI() + "?" + request.getQueryString();
            } else {
                interceptedUri = request.getRequestURI();
            }

            session.setAttribute("interceptedUri", interceptedUri);
            response.setStatus(303);
            // TODO Apply authorization server from application properties
            // response.setHeader("Location", "http://localhost:7777/authorize?response_type=code" +
            response.setHeader("Location", "https://noauth-ga2speboxa-ew.a.run.app/authorize?response_type=code" +
                "&client_id=jnebdD0fczAHoEBVrr6lE7OAuYchc2ZR" +
                "&redirect_uri=http://localhost:8080/bff/callback" +
                "&scope=offline_access%20read%3Apatients" +
                "&audience=https://ksch-workflows.github.io/api");
            return false;
        } else {
            return true;
        }
    }

    private static boolean isWebPageRequest(HttpServletRequest request) {
        var requestURI = request.getRequestURI();
        return requestURI.endsWith("/") || requestURI.endsWith(".html");
    }

    private static boolean isSessionRequest(HttpServletRequest request) {
        var requestURI = request.getRequestURI();
        return requestURI.startsWith("/bff/session");
    }
}
