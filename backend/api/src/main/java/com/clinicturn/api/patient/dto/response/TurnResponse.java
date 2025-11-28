package com.clinicturn.api.patient.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TurnResponse(
    Long id,
    TurnSpecialityResponse speciality,
    TurnPatientResponse patient,
    TurnDoctorResponse doctor,
    String reason,
    String status,
    Instant calledAt,
    Instant seenAt,
    Instant completedAt,
    Instant cancelledAt,
    Instant createdAt,
    Instant updatedAt
) {
    @Builder
    public record TurnSpecialityResponse(
            Long id,
            String code
    ){}

    @Builder
    public record TurnPatientResponse(
            Long id,
            String fullName
    ){}

    @Builder
    public record TurnDoctorResponse(
            Long id,
            String fullName
    ){}
}
