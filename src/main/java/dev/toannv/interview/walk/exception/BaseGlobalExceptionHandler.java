package dev.toannv.interview.walk.exception;

import brave.Span;
import brave.Tracer;
import dev.toannv.interview.walk.web.handler.RestErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.util.Objects;


public class BaseGlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseGlobalExceptionHandler.class);

    @Autowired
    private Tracer tracer;

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<RestErrorResponse> handleIllegalArgumentException(final Exception e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestErrorResponse.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), getTraceId()));
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<RestErrorResponse> handleValidationException(ValidationException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestErrorResponse.of(e.getErrorCode(), e.getMessage(), getTraceId()));
    }

    @ExceptionHandler({ MultipartException.class })
    public ResponseEntity<RestErrorResponse> handleMultipartException(final MultipartException exception) {
        if (Objects.requireNonNull(exception.getMessage()).contains("request was larger than")) {
            LOGGER.error(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(RestErrorResponse.of(String.valueOf(HttpStatus.PAYLOAD_TOO_LARGE.value()), HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase(), getTraceId()));
        }
        if (Objects.requireNonNull(exception.getMessage()).contains("Maximum upload size exceeded")) {
            LOGGER.error("Maximum upload size each files exceeded");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestErrorResponse.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Maximum upload size each files exceeded", getTraceId()));
        }
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RestErrorResponse.of(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage(), getTraceId()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RestErrorResponse> handleException(final Throwable ex) {
        LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        final String errorMessage;
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof AuthenticationException) {
            code = HttpStatus.UNAUTHORIZED;
            errorMessage = "Your account is disabled, please contact the administrator.";
        } else if (ex instanceof AccessDeniedException) {
            code = HttpStatus.FORBIDDEN;
            errorMessage = "You do not have permission to use this action. Please contact the site administrator to request access.";
        } else if (ex instanceof MethodArgumentNotValidException || ex instanceof MethodArgumentTypeMismatchException) {
            code = HttpStatus.BAD_REQUEST;
            errorMessage = "An unexpected mandatory attributes.";
        } else {
            errorMessage = "An unexpected exception occurred. Please try again or send a support request to administrators.";
        }

        return new ResponseEntity<>(RestErrorResponse.of(String.valueOf(code.value()), errorMessage, tracer.currentSpan().context().traceIdString()), code);
    }

    public String getTraceId() {
        if (Objects.nonNull(tracer)) {
            Span currentSpan = tracer.currentSpan();
            if (null != currentSpan) {
                return currentSpan.context().traceIdString();
            }
        }
        return null;
    }
}
