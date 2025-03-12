package com.projeto.ecommercee.exception;

import com.projeto.ecommercee.dto.error.ErrorResponse;
import com.projeto.ecommercee.dto.error.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingAddressException.class)
    public ResponseEntity<ErrorResponse> handleMissingAddressException(MissingAddressException ex,
                                                                       HttpServletRequest request) {
        return builderResponseException(HttpStatus.BAD_REQUEST, "Bad Request", ex, request);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex,
                                                                             HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                          HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }


    @ExceptionHandler(UsernameNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotExistsException(UsernameNotExistsException ex,
                                                                          HttpServletRequest request) {
        return builderResponseException(HttpStatus.NOT_FOUND, "Not Found", ex, request);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex,
                                                                          HttpServletRequest request) {
        return builderResponseException(HttpStatus.CONFLICT, "Conflict", ex, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
                                                               HttpServletRequest request) {
        return builderResponseException(HttpStatus.FORBIDDEN, "Forbidden", ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                  HttpServletRequest request) {
        return builderResponseException(HttpStatus.BAD_REQUEST, "Bad Request", ex, request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex,
                                                                  HttpServletRequest request) {
        return builderResponseException(HttpStatus.NOT_FOUND, "Not Found", ex, request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex,
                                                                      HttpServletRequest request) {
        return builderResponseException(HttpStatus.NOT_FOUND, "Not Found", ex, request);
    }

    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException ex,
                                                                        HttpServletRequest request) {
        return builderResponseException(HttpStatus.UNAUTHORIZED, "Unauthorized", ex, request);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    public ResponseEntity<ErrorResponse> builderResponseException(HttpStatus status, String error, Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), error, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
 