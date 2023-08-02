package dev.toannv.interview.walk.web.handler;


import java.util.Date;

public class RestErrorResponse {

    private Date timestamp;
    private String error;
    private String errorCode;
    private String correlationId;

    public static RestErrorResponse of(final String errorCode) {
        return of(errorCode, null);
    }

    public static RestErrorResponse of(final String errorCode, final String errorMessage) {
        final RestErrorResponse errorResponse = new RestErrorResponse();
        errorResponse.setErrorCode(errorCode);
        errorResponse.setError(errorMessage);
        errorResponse.setTimestamp(new Date());

        return errorResponse;
    }

    public static RestErrorResponse of(final String errorCode, final String errorMessage, String correlationId) {
        final RestErrorResponse errorResponse = new RestErrorResponse();
        errorResponse.setErrorCode(errorCode);
        errorResponse.setError(errorMessage);
        errorResponse.setCorrelationId(correlationId);
        errorResponse.setTimestamp(new Date());

        return errorResponse;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

}
