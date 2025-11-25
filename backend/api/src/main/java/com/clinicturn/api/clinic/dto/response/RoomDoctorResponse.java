package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RoomDoctorResponse(
        Long id,
        Long doctorId,
        String doctorName,
        Long roomId,
        Integer roomNumber,
        Instant assignedAt
) {}
