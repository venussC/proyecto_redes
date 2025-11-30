package com.clinicturn.api.auth.service;

import com.clinicturn.api.auth.dto.request.CreateClinicUserRequest;
import com.clinicturn.api.auth.dto.request.UpdateClinicUserRequest;
import com.clinicturn.api.auth.model.ClinicUser;

public interface ClinicUserService {

    ClinicUser createAndReturnEntity(CreateClinicUserRequest request);

    ClinicUser updateAndReturnEntity(Long pathId, UpdateClinicUserRequest request);

    ClinicUser findByIdAndReturnEntity(Long id);

    ClinicUser findByUsernameAndReturnEntity(String username);
}
