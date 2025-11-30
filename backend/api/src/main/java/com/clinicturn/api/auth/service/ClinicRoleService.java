package com.clinicturn.api.auth.service;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleRequest;
import com.clinicturn.api.auth.model.ClinicRole;
import com.clinicturn.api.auth.model.ClinicRoleType;

public interface ClinicRoleService {

    ClinicRole create(CreateClinicRoleRequest request);

    ClinicRole findByCode(ClinicRoleType code);
}