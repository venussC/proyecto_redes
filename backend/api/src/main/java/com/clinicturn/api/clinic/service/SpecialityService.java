package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateSpecialityRequest;
import com.clinicturn.api.clinic.dto.response.SpecialityResponse;
import com.clinicturn.api.clinic.model.Speciality;

import java.util.List;

public interface SpecialityService {

    SpecialityResponse create(CreateSpecialityRequest request);

    SpecialityResponse getById(Long id);

    Speciality getByCodeAndReturnEntity(String code);

    List<SpecialityResponse> getAll();
}
