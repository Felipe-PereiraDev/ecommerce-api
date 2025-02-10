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
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");
        response.put("message", ex.getMessage());
        response.put("path", "/products");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");
        response.put("message", ex.getMessage());
        response.put("path", "/user");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(UsernameNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotExistsException(UsernameNotExistsException ex,
                                                                                HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStockException(InsufficientStockException ex,
                                                                                HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
