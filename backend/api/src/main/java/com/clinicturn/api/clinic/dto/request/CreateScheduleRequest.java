package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class CreateScheduleRequest {

    @NotNull(message = "CreateScheduleRequest's clinicId should not be null")
    private Long clinicId;

    @NotNull(message = "CreateScheduleRequest's weekDay should not be null")
    private DayOfWeek weekDay;

    private LocalTime opening;

    private LocalTime closing;

    private Boolean isClosed;
}
