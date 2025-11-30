package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record DoctorResponse(
        Long id,
        String fullName,
        SpecialityResponse speciality,
        String email,
        String phoneNumber,
        Boolean isActive,
        Instant createAt,
        Instant updatedAt,
        RoomResponse room
) {}
