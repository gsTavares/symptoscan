package com.io.health.service.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse<T> {

    private Integer status;
    private T body;
    private String message;

    public ApiResponse() {
    }

    public ResponseEntity<ApiResponse<T>> ok(T body, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setBody(body);
        response.setMessage(message);
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<ApiResponse<Collection<T>>> okGet(Collection<T> body, String message) {
        ApiResponse<Collection<T>> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setBody(body);
        response.setMessage(message);
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<ApiResponse<T>> badRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        return ResponseEntity.badRequest().body(response);
    }

    public ResponseEntity<ApiResponse<T>> notFound(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(message);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public ResponseEntity<ApiResponse<Collection<T>>> notFoundGet(String message) {
        ApiResponse<Collection<T>> response = new ApiResponse<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(message);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public ResponseEntity<ApiResponse<T>> notAcceptable(List<String> messages) {
        ApiResponse<T> response = new ApiResponse<>();
        String message = messages.stream()
                .collect(Collectors.joining(";\n"));
        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        response.setMessage(message);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
