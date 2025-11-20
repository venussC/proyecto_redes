package com.clinicturn.api.auth.exception.handler;

import com.clinicturn.api.auth.exception.EmptyRolesException;
import com.clinicturn.api.auth.exception.RefreshTokenAlreadyExpiredException;
import com.clinicturn.api.auth.exception.RefreshTokenMismatchException;
import com.clinicturn.api.common.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(RefreshTokenAlreadyExpiredException.class)
    public ResponseEntity<ApiErrorResponse> refreshTokenAlreadyExpiredExceptionHandler(RefreshTokenAlreadyExpiredException ex,
                                                                             HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Refresh Token Is Already Revoked or Expired")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(RefreshTokenMismatchException.class)
    public ResponseEntity<ApiErrorResponse> refreshTokenMismatchExceptionHandler(RefreshTokenMismatchException ex,
                                                                                       HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Refresh Token Mismatch")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(EmptyRolesException.class)
    public ResponseEntity<ApiErrorResponse> emptyRolesExceptionHandler(EmptyRolesException ex,
                                                                                 HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("User With Empty Roles")
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
}
