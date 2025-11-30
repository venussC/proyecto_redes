package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateRoomDoctorRequest;
import com.clinicturn.api.clinic.dto.response.RoomDoctorResponse;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class RoomDoctorController {

    private final RoomDoctorService roomDoctorService;

    @PostMapping("/doctor/assign-room")
    public ResponseEntity<RoomDoctorResponse> assignRoom(@Valid @RequestBody CreateRoomDoctorRequest request) {
        RoomDoctorResponse response = roomDoctorService.assignRoom(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
