package dev.toannv.interview.walk.exception;

import dev.toannv.interview.walk.web.handler.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestGlobalExceptionHandler extends BaseGlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body(RestErrorResponse.of(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), ex.getMessage(),
                        this.getTraceId()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestErrorResponse> bindExceptionHandler(BindException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(RestErrorResponse.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage(), this.getTraceId()));
    }

}
