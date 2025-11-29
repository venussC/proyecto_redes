package com.clinicturn.api.patient.controller;

import com.clinicturn.api.patient.dto.request.CreatePatientTurnRequest;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import com.clinicturn.api.patient.service.CreatePatientTurnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/turn")
public class TurnController {

    private final CreatePatientTurnService createService;

    @PostMapping()
    public ResponseEntity<TurnResponse> create(@Valid @RequestBody CreatePatientTurnRequest request,
                                               Authentication authentication) {
        TurnResponse response = createService.create(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
