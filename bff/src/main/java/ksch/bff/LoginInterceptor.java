package ksch.bff;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api") || requestURI.startsWith("/bff") || requestURI.equals("/error") || requestURI.equals("/favicon.ico")) {
            return true;
        }
        var session = request.getSession();
        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {
            response.setStatus(303);
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
}
