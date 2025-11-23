package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateScheduleRequest;
import com.clinicturn.api.clinic.dto.response.ClinicScheduleResponse;
import com.clinicturn.api.clinic.dto.response.ScheduleResponse;
import com.clinicturn.api.clinic.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponse> create(CreateScheduleRequest request) {
        ScheduleResponse response = scheduleService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clinic/{clinicId}/schedules")
    public ResponseEntity<ClinicScheduleResponse> getSchedulesFromClinic(@PathVariable Long clinicId) {
        ClinicScheduleResponse response = scheduleService.getSchedulesFromClinic(clinicId);
        return ResponseEntity.ok(response);
    }
}
