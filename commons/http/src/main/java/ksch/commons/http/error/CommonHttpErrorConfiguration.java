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
package ksch.commons.http.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public
class CommonHttpErrorConfiguration {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        var responseBody = ErrorResponseBody.builder()
                .errorId("entity-not-found")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DeserializationException.class})
    public ResponseEntity<Object> handleDeserializationException(DeserializationException exception) {
        log.error("Payload could not be deserialized into a Java type.", exception);
        var responseBody = ErrorResponseBody.builder()
                .errorId( "deserialization-error")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public JsonResponseEntity<ErrorResponseBody> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception
    ) {
        log.warn("Someone called the API in a way which is not intended.", exception);
        var details = new HashMap<>();
        details.put("name", exception.getParameterName());
        details.put("type", exception.getParameterType());

        var responseBody = ErrorResponseBody.builder()
                .errorId("request-parameter-missing")
                .details(details)
                .build();

        return new JsonResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnknownException(Exception exception) {
        log.error("Missing error handler for exception of type {}. The dev team should create an error handler, so " +
                        "that more details can be determined for the error which has happened.",
                exception.getClass().getTypeName(), exception
        );
        var responseBody = ErrorResponseBody.builder()
                .errorId("unknown-error")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    private static class JsonResponseEntity<T> extends ResponseEntity<T> {

        public JsonResponseEntity(T body, HttpStatus status) {
            super(body, headers(), status);
        }

        private static HttpHeaders headers() {
            var result = new HttpHeaders();
            result.add("content-type", "application/json");
            return result;
        }
    }
}
