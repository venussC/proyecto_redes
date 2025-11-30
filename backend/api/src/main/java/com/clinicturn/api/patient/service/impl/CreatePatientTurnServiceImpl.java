package com.clinicturn.api.patient.service.impl;

import com.clinicturn.api.patient.dto.request.CreatePatientRequest;
import com.clinicturn.api.patient.dto.request.CreatePatientTurnRequest;
import com.clinicturn.api.patient.dto.request.CreateTurnRequest;
import com.clinicturn.api.patient.dto.response.PatientResponse;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import com.clinicturn.api.patient.service.CreatePatientTurnService;
import com.clinicturn.api.patient.service.PatientService;
import com.clinicturn.api.patient.service.TurnService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatePatientTurnServiceImpl implements CreatePatientTurnService {

    private final PatientService patientService;
    private final TurnService turnService;

    @Override
    @Transactional
    public TurnResponse create(CreatePatientTurnRequest request, Authentication authentication) {
        CreatePatientRequest patientRequest = mapToPatientRequest(request);
        PatientResponse patientResponse = patientService.create(patientRequest, authentication.getName());
        CreateTurnRequest turnRequest = mapToTurnRequest(request, patientResponse.id());
        return turnService.create(turnRequest, authentication);
    }

    private CreatePatientRequest mapToPatientRequest(CreatePatientTurnRequest request) {
        return CreatePatientRequest.builder()
                .fullName(request.getFullName())
                .dni(request.getDni())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    private CreateTurnRequest mapToTurnRequest(CreatePatientTurnRequest request, Long patientId) {
        return CreateTurnRequest.builder()
                .specialityId(request.getSpecialityId())
                .patientId(patientId)
                .reason(request.getReason())
                .build();
    }
}
