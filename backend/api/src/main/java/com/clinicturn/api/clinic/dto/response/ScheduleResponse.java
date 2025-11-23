package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record ScheduleResponse(
        Long id,
        Long clinicId,
        String weekDay,
        LocalTime opening,
        LocalTime closing,
        Boolean isClosed
) {}
