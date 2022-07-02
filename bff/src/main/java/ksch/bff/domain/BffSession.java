package ksch.bff.domain;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class BffSession {

    private final HttpSession session;

    public BffSession(HttpSession session) {
        this.session = session;
    }

    public Optional<String> getAccessToken() {
        var accessToken = session.getAttribute("accessToken");
        if (accessToken == null) {
            return Optional.empty();
        }
        return Optional.of(accessToken.toString());
    }
}
