package com.clinicturn.api.patient.exception.handler;

import com.clinicturn.api.common.dto.response.ApiErrorResponse;
import com.clinicturn.api.patient.exception.InvalidStateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class PatientExceptionHandler {

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ApiErrorResponse> invalidStateExceptionHandler(InvalidStateException ex,
                                                                         HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Invalid State")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
