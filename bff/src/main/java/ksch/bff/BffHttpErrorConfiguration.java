package ksch.bff;

import ksch.commons.http.error.ErrorResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Maps exception from the BFF module to HTTP error responses.
 */
@ControllerAdvice
@Slf4j
class BffHttpErrorConfiguration {

    @ExceptionHandler({OAuthException.class})
    public ResponseEntity<Object> handleOAuthException(OAuthException exception) {
        var responseBody = ErrorResponseBody.builder()
                .errorId("oauth-failure")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), UNAUTHORIZED);
    }
}
