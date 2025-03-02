package com.projeto.ecommercee.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingAddressException.class)
    public ResponseEntity<Map<String, Object>> handleMissingAddressException(MissingAddressException ex,
                                                                             HttpServletRequest request) {
        return builderResponseException(HttpStatus.BAD_REQUEST, "Bad Request", ex, request);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleProductAlreadyExistsException(ProductAlreadyExistsException ex,
                                                                                   HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                                HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }


    @ExceptionHandler(UsernameNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotExistsException(UsernameNotExistsException ex,
                                                                                HttpServletRequest request) {
        return builderResponseException(HttpStatus.NOT_FOUND, "Not Found", ex, request);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }

    public ResponseEntity<Map<String, Object>> builderResponseException(HttpStatus status, String error, Exception ex, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }
}
 