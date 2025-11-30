package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

@Builder
public record ClinicFullResponse(
        Long id,
        String name,
        String address,
        String phoneNumber,
        Double latitude,
        Double longitude,
        List<ScheduleResponse> schedules,
        Instant createdAt,
        Instant updatedAt
) {
    @Builder
    public record ScheduleResponse(
            String day,
            LocalTime opening,
            LocalTime closing,
            Boolean isClosed
    ) {}
}
