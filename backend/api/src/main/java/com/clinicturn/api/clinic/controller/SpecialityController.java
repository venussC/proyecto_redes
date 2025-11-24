package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateSpecialityRequest;
import com.clinicturn.api.clinic.dto.response.SpecialityResponse;
import com.clinicturn.api.clinic.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class SpecialityController {

    private final SpecialityService specialityService;

    @PostMapping("/speciality")
    public ResponseEntity<SpecialityResponse> create(CreateSpecialityRequest request) {
        SpecialityResponse response = specialityService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/speciality/{id}")
    public ResponseEntity<SpecialityResponse> getById(@PathVariable Long id) {
        SpecialityResponse response = specialityService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/speciality")
    public ResponseEntity<List<SpecialityResponse>> getAll() {
        List<SpecialityResponse> response = specialityService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
