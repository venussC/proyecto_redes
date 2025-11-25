package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateDoctorRequest;
import com.clinicturn.api.clinic.dto.request.UpdateDoctorRequest;
import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.clinic.model.Doctor;

import java.util.List;

public interface DoctorService {

    DoctorResponse create(CreateDoctorRequest request);

    DoctorResponse update(Long id, UpdateDoctorRequest request);

    DoctorResponse getById(Long id);

    Doctor getByIdAndReturnEntity(Long id);

    List<DoctorResponse> getAll();

    List<DoctorResponse> getByIsActiveTrue();

    List<DoctorResponse> getByIsActiveTrueAndSpecialityCode(String code);
}
