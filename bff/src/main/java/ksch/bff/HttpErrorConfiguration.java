package ksch.bff;

import ksch.commons.http.error.ErrorResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component("bffHttpErrorConfiguration")
@ControllerAdvice
@Slf4j
class HttpErrorConfiguration {

    @ExceptionHandler({OAuthException.class})
    public ResponseEntity<Object> handleOAuthException(OAuthException exception) {
        var responseBody = ErrorResponseBody.builder()
                .message("Failure in OAuth process")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
