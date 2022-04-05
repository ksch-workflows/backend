package ksch.bff;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        var req = new CustomizedRequest(request);
        if (isApiRequest(req) && hasSessionCookie(req)) {
            var session = getMandatorySession(req);
            var accessToken = session.getAttribute("accessToken");
            if (accessToken != null) {
                // TODO Refresh access token if necessary
                req.putHeader("Authorization", "Bearer " + accessToken);
            }
        }
        chain.doFilter(req, response);
    }

    private static boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

    private static boolean hasSessionCookie(HttpServletRequest request) {
        var cookies = request.getCookies();
        for (var cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("JSESSIONID")) {
                return true;
            }
        }
        return false;
    }

    private static HttpSession getMandatorySession(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("No existing session found.");
        }
        return session;
    }
}
