package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateClinicRequest;
import com.clinicturn.api.clinic.dto.response.ClinicFullResponse;
import com.clinicturn.api.clinic.dto.response.ClinicResponse;
import com.clinicturn.api.clinic.model.Clinic;

import java.util.List;

public interface ClinicService {

    ClinicResponse create(CreateClinicRequest request);

    ClinicFullResponse getById(Long id);

    Clinic findByIdAndReturnEntity(Long id);

    void assertExistsById(Long id);

    List<ClinicResponse> getAll();
}
