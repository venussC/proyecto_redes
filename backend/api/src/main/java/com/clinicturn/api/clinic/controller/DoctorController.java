package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateDoctorRequest;
import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.clinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping()
    public ResponseEntity<DoctorResponse> create(@Valid @RequestBody CreateDoctorRequest request) {
        DoctorResponse response = doctorService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        DoctorResponse response = doctorService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<DoctorResponse>> getActiveDoctorsBySpeciality(
            @RequestParam(required = false) String speciality) {
        if (speciality != null) {
            List<DoctorResponse> filtered = doctorService.getByIsActiveTrueAndSpecialityCode(speciality);
            return ResponseEntity.ok(filtered);
        }
        List<DoctorResponse> all = doctorService.getByIsActiveTrue();
        return ResponseEntity.ok(all);
    }
}
