package com.clinicturn.api.patient.service;

import com.clinicturn.api.patient.dto.request.CreateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.TurnStatusResponse;
import com.clinicturn.api.patient.model.TurnStatus;

import java.util.List;

public interface TurnStatusService {

    TurnStatusResponse create(CreateTurnStatusRequest request);

    TurnStatusResponse getById(Long id);

    TurnStatus getByIdAndReturnEntity(Long id);

    TurnStatusResponse getByName(String name);

    TurnStatus getByNameAndReturnEntity(String name);

    List<TurnStatusResponse> getAll();
}
