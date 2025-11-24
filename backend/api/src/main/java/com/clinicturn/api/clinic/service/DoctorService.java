package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateDoctorRequest;
import com.clinicturn.api.clinic.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    DoctorResponse create(CreateDoctorRequest request);

    DoctorResponse getById(Long id);

    List<DoctorResponse> getByIsActiveTrue();

    List<DoctorResponse> getByIsActiveTrueAndSpecialityCode(String code);
}
