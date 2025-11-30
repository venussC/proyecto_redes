package com.clinicturn.api.clinic.dto.response;

import java.time.LocalTime;
import java.util.List;

public record ClinicScheduleResponse(
        Long clinicId,
        String clinicName,
        List<ScheduleInfo> schedules
) {
    public record ScheduleInfo(
            String day,
            LocalTime opening,
            LocalTime closing,
            Boolean isClosed
    ){}
}
