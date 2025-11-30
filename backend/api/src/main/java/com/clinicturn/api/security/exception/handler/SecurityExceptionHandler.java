package com.clinicturn.api.security.exception.handler;

import com.clinicturn.api.common.dto.response.ApiErrorResponse;
import com.clinicturn.api.security.exception.ClinicUserDisabledException;
import com.clinicturn.api.security.exception.ClinicUserLockedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(ClinicUserDisabledException.class)
    public ResponseEntity<ApiErrorResponse> ClinicUserDisabledExceptionHandler(ClinicUserDisabledException ex,
                                                                               HttpServletRequest request) {

        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("User Disabled")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ClinicUserLockedException.class)
    public ResponseEntity<ApiErrorResponse> ClinicUserLockedExceptionHandler(ClinicUserLockedException ex,
                                                                               HttpServletRequest request) {

        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("User Locked")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

}
