package com.clinicturn.api.auth.service;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleUserRequest;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicRoleUser;

import java.util.List;

public interface ClinicRoleUserService {

    ClinicRoleUser create(CreateClinicRoleUserRequest request);

    boolean existsByRoleIdAndUserId(Long roleId, Long userId);

    boolean existsByRoleTypeCodeAndUsername(ClinicRoleType code, String username);

    List<ClinicRoleType> findRoleCodesByUserId(Long userId);

    ClinicRoleUser findByUserUsername(String username);
}
