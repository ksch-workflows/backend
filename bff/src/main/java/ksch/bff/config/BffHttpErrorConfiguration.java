/*
 * Copyright 2022 KS-plus e.V.
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
package ksch.bff.config;

import ksch.bff.util.OAuthException;
import ksch.bff.util.ProtocolViolationException;
import ksch.commons.http.error.ErrorResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
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

    @ExceptionHandler({ProtocolViolationException.class})
    public ResponseEntity<Object> handleProtocolViolation(ProtocolViolationException exception) {
        log.warn("This exception might indicate a client which doesn't know how to use the API correctly" +
                "or an attaker trying to probe the system.", exception);
        var responseBody = ErrorResponseBody.builder()
                .errorId("protocol-violation")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), FORBIDDEN);
    }
}
