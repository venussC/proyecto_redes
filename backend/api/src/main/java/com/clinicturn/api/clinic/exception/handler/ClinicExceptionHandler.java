package com.clinicturn.api.clinic.exception.handler;

import com.clinicturn.api.clinic.exception.InactiveRoomException;
import com.clinicturn.api.clinic.exception.InvalidSpecialityCodeException;
import com.clinicturn.api.clinic.exception.UnavailableRoomException;
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

    @ExceptionHandler(InactiveRoomException.class)
    public ResponseEntity<ApiErrorResponse> inactiveRoomExceptionHandler(InactiveRoomException ex,
                                                                         HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Inactive Room")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UnavailableRoomException.class)
    public ResponseEntity<ApiErrorResponse> unavailableRoomExceptionHandler(UnavailableRoomException ex,
                                                                            HttpServletRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .title("Unavailable Room")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(Instant.now())
                .errors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
