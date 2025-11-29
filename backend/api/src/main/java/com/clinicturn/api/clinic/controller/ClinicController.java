package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateClinicRequest;
import com.clinicturn.api.clinic.dto.response.ClinicFullResponse;
import com.clinicturn.api.clinic.dto.response.ClinicResponse;
import com.clinicturn.api.clinic.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping("/clinic")
    public ResponseEntity<ClinicResponse> create(@Valid @RequestBody CreateClinicRequest request) {
        ClinicResponse response = clinicService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity<ClinicFullResponse> getById(@PathVariable Long id) {
        ClinicFullResponse response = clinicService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
