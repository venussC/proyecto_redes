package com.clinicturn.api.patient.controller;

import com.clinicturn.api.patient.dto.response.TurnStatusResponse;
import com.clinicturn.api.patient.service.TurnStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/turn/status")
public class TurnStatusController {

    private final TurnStatusService service;

    @GetMapping()
    public ResponseEntity<List<TurnStatusResponse>> getAll() {
        List<TurnStatusResponse> response = service.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnStatusResponse> getById(@PathVariable Long id) {
        TurnStatusResponse response = service.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<TurnStatusResponse> getByName(@PathVariable String name) {
        TurnStatusResponse response = service.getByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
