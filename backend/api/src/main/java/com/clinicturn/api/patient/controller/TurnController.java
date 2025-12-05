package com.clinicturn.api.patient.controller;

import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.patient.dto.request.CreatePatientTurnRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnDoctorRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.PacientCountResponse;
import com.clinicturn.api.patient.dto.response.TurnCountResponse;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import com.clinicturn.api.patient.service.CreatePatientTurnService;
import com.clinicturn.api.patient.service.TurnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/turn")
public class TurnController {

    private final CreatePatientTurnService createService;
    private final TurnService turnService;

    @PostMapping()
    public ResponseEntity<TurnResponse> create(@Valid @RequestBody CreatePatientTurnRequest request,
                                               Authentication authentication) {
        TurnResponse response = createService.create(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TurnResponse>> getAll() {
        List<TurnResponse> response = turnService.getAllActive();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/count")
    public ResponseEntity<PacientCountResponse> getPacientCount() {
        PacientCountResponse response = turnService.getPacientCount();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/count/waiting")
    public ResponseEntity<TurnCountResponse> getTurnCountByWaitingStatus() {
        TurnCountResponse response = turnService.countWaitingTurns();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/count/seen")
    public ResponseEntity<TurnCountResponse> getTurnCountBySeenStatus() {
        TurnCountResponse response = turnService.countSeenTurns();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/last-called")
    public ResponseEntity<TurnResponse> getLastTurnCalled() {
        TurnResponse response = turnService.getLastCalledTurn();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TurnResponse> changeStatus(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateTurnStatusRequest request) {
        TurnResponse response = turnService.updateStatus(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}/doctor")
    public ResponseEntity<List<DoctorResponse>> getAvailableDoctorsByTurn(@PathVariable Long id) {
        List<DoctorResponse> response = turnService.getAvailableDoctorsByTurnId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{id}/doctor")
    public ResponseEntity<TurnResponse> assignDoctor(@PathVariable Long id,
                                                     @Valid @RequestBody UpdateTurnDoctorRequest request) {
        TurnResponse response = turnService.updateDoctor(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
