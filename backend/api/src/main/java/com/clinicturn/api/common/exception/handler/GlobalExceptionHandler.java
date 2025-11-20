package com.clinicturn.api.common.exception.handler;

import com.clinicturn.api.common.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> ValidationExceptionHandler(MethodArgumentNotValidException ex,
                                                                       HttpServletRequest request) {
        List<ApiErrorResponse.FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ApiErrorResponse.FieldErrorDetail(
                        err.getField(), err.getDefaultMessage()))
                .toList();

        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Validation Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("One or more fields failed validation.")
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException ex,
                                                                            HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Illegal Argument")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> BadCredentialsExceptionHandler(BadCredentialsException ex,
                                                                           HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Bad Credentials")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
