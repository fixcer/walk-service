package dev.toannv.interview.walk.exception;

public class BaseRuntimeException extends RuntimeException {
    private String errorCode;

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, String code) {
        super(message);
        this.errorCode = code;
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause, String code) {
        super(cause);
        this.errorCode = code;
    }

    public BaseRuntimeException(String message, String code, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
