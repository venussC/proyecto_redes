package com.clinicturn.api.clinic.exception.handler;

import com.clinicturn.api.clinic.exception.InvalidSpecialityCodeException;
import com.clinicturn.api.common.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ClinicExceptionHandler {

    @ExceptionHandler(InvalidSpecialityCodeException.class)
    public ResponseEntity<ApiErrorResponse> invalidSpecialityCodeExceptionHandler(InvalidSpecialityCodeException ex,
                                                                           HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Invalid Speciality Code")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
