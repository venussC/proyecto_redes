package com.clinicturn.api.clinic.controller;

import com.clinicturn.api.clinic.dto.request.CreateRoomRequest;
import com.clinicturn.api.clinic.dto.response.RoomResponse;
import com.clinicturn.api.clinic.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<RoomResponse> create(CreateRoomRequest request) {
        RoomResponse response = roomService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable Long id) {
        RoomResponse response = roomService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/clinic/{clinicId}/rooms/available")
    public ResponseEntity<List<RoomResponse>> getAllAvailableByClinic(@PathVariable Long clinicId) {
        List<RoomResponse> responses = roomService.getAllAvailablesByClinicId(clinicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }
}
