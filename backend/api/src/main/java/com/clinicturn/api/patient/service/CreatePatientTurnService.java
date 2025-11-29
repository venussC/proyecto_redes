package com.clinicturn.api.patient.service;

import com.clinicturn.api.patient.dto.request.CreatePatientTurnRequest;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import org.springframework.security.core.Authentication;

public interface CreatePatientTurnService {

    TurnResponse create(CreatePatientTurnRequest request, Authentication authentication);
}
