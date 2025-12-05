package com.clinicturn.api.patient.service;

import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.patient.dto.request.CreateTurnRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnDoctorRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TurnService {

    TurnResponse create(CreateTurnRequest request, Authentication authentication);

    TurnResponse updateStatus(Long id, UpdateTurnStatusRequest request);

    TurnResponse updateDoctor(Long id, UpdateTurnDoctorRequest request);

    List<TurnResponse> getAllActive();

    List<DoctorResponse> getAvailableDoctorsByTurnId(Long id);
}
