package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateClinicRequest;
import com.clinicturn.api.clinic.dto.response.ClinicResponse;
import com.clinicturn.api.clinic.model.Clinic;

public interface ClinicService {

    ClinicResponse create(CreateClinicRequest request);

    Clinic findByIdAndReturnEntity(Long id);

    void assertExistsById(Long id);
}
