package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateScheduleRequest;
import com.clinicturn.api.clinic.dto.response.ClinicScheduleResponse;
import com.clinicturn.api.clinic.dto.response.ScheduleResponse;

public interface ScheduleService {

    ScheduleResponse create(CreateScheduleRequest request);

    ClinicScheduleResponse getSchedulesFromClinic(Long id);
}