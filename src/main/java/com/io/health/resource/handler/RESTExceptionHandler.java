package com.io.health.resource.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.io.health.service.util.ApiResponse;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RESTExceptionHandler {

    @Autowired
    private ApiResponse<Object> exceptionResponse;

    private String joinMessages(List<String> messages) {
        return messages.stream().collect(Collectors.joining(";\n"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            messages.add(errorMessage);
        });
        String message = joinMessages(messages);

        return exceptionResponse.badRequest(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        List<String> messages = Arrays.asList(ex.getMessage());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setMessage(messages.stream().collect(Collectors.joining(";\n")));

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> messages = new ArrayList<>();

        ex.getConstraintViolations().forEach(cv -> {
            messages.add(cv.getMessageTemplate());
        });

        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setMessage(messages.stream().collect(Collectors.joining(";\n")));

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Object> handleJwtDecodeException(JWTDecodeException jde) {
        exceptionResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionResponse.setMessage(jde.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exceptionResponse);
    }
}
