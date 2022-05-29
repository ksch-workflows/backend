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

import ksch.commons.http.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        var responseBody = ErrorResponseBody.builder()
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DeserializationException.class})
    public ResponseEntity<Object> handleDeserializationException(DeserializationException exception) {
        var responseBody = ErrorResponseBody.builder()
                .errorId(exception.getErrorId())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO Add test for handling of unknown error
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnknownException(Exception exception) {
        log.error("Missing error handler. The dev team should create an error handler, so that more details can be determined for the error which has happened.", exception);
        var responseBody = ErrorResponseBody.builder()
                .errorId("unknown-error")
                .message("An error occurred.")
                .build();
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO Map unhandled exception to 500 "unknown-error" "An error occurred."

    @AllArgsConstructor
    @Builder
    @Getter
    private static class ErrorResponseBody {
        private String message;
        private String errorId;
    }
}
