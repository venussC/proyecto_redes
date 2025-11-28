package com.clinicturn.api.patient.controller;

import com.clinicturn.api.patient.dto.request.CreateTurnRequest;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import com.clinicturn.api.patient.service.TurnService;
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

    private final TurnService turnService;

    @PostMapping()
    public ResponseEntity<TurnResponse> create(@Valid @RequestBody CreateTurnRequest request,
                                               Authentication authentication) {
        TurnResponse response = turnService.create(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
