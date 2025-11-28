package com.clinicturn.api.patient.service;

import com.clinicturn.api.patient.dto.request.CreateTurnRequest;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import org.springframework.security.core.Authentication;

public interface TurnService {

    TurnResponse create(CreateTurnRequest request, Authentication authentication);
}
