package dev.toannv.interview.walk.exception;

public class ValidationException extends BaseRuntimeException {

    public ValidationException(String code) {
        super(code);
    }

    public ValidationException(String message, String code) {
        super(message, code);
    }

    public ValidationException(Throwable cause, String code) {
        super(cause, code);
    }

    public ValidationException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

}
