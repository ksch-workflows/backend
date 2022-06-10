package ksch.commons.http.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponseBody {
    private String message;
    private String errorId;
    private Object details;
}
