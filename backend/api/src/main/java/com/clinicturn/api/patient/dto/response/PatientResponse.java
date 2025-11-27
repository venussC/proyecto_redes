package com.clinicturn.api.patient.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PatientResponse(
        Long id,
        Long userId,
        String fullName,
        String dni,
        String phoneNumber,
        Instant createdAt
) {}
