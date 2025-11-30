package com.clinicturn.api.patient.service;

import com.clinicturn.api.patient.dto.request.CreatePatientRequest;
import com.clinicturn.api.patient.dto.response.PatientResponse;
import com.clinicturn.api.patient.model.Patient;

public interface PatientService {

    PatientResponse getMe(String username);

    PatientResponse create(CreatePatientRequest request, String username);

    Patient getByIdAndReturnEntity(Long id);
}
