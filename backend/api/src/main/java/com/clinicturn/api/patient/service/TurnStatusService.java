package com.clinicturn.api.patient.service;

import com.clinicturn.api.patient.dto.request.CreateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.TurnStatusResponse;

import java.util.List;

public interface TurnStatusService {

    TurnStatusResponse create(CreateTurnStatusRequest request);

    TurnStatusResponse getById(Long id);

    TurnStatusResponse getByName(String name);

    List<TurnStatusResponse> getAll();
}
