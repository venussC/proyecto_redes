package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ClinicResponse(
        Long id,
        String name,
        String address,
        String phoneNumber,
        Double latitude,
        Double longitude,
        Instant createdAt,
        Instant updatedAt
) {}
