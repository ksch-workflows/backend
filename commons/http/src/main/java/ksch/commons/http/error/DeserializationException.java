package ksch.commons.http.error;

public class DeserializationException extends RuntimeException implements WithClientResponse {

    @Override
    public String getErrorId() {
        return "deserialization-error";
    }

    @Override
    public String getMessage() {
        return null;
    }
}
