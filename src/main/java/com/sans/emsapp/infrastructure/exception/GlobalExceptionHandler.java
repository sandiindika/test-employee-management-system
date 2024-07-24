package com.sans.emsapp.infrastructure.exception;

import com.sans.emsapp.adapter.config.constant.ResponseMessage;
import com.sans.emsapp.adapter.config.utils.DatetimeUtil;
import com.sans.emsapp.adapter.dto.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final DatetimeUtil datetime;

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse response = ErrorResponse.builder()
                .code(status.value())
                .error(error)
                .message(message)
                .timestamp(datetime.generateDatetime())
                .build();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleServletException(ServletException e) {
        return buildResponse(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR_INTERNAL, e.getMessage());
    }
}
