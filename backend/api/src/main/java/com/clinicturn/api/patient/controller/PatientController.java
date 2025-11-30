package com.clinicturn.api.patient.controller;

import com.clinicturn.api.patient.dto.response.PatientResponse;
import com.clinicturn.api.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/me")
    public ResponseEntity<PatientResponse> me(Authentication authentication) {
        PatientResponse response = patientService.getMe(authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
