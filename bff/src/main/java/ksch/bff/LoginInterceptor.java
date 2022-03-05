package ksch.bff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var session = request.getSession();

        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {
            response.setStatus(303);
            response.setHeader("Location", "https://ksch-workflows.eu.auth0.com/authorize?response_type=code" +
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
