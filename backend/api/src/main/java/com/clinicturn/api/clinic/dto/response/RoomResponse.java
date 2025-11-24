package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RoomResponse(
        Long id,
        Long clinicId,
        Integer roomNumber,
        Boolean isActive,
        Boolean isAvailable,
        Instant createdAt,
        Instant updatedAt
) {}
