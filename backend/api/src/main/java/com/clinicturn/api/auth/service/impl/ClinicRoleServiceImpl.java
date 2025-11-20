package com.clinicturn.api.auth.service.impl;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleRequest;
import com.clinicturn.api.auth.model.ClinicRole;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.repository.ClinicRoleRepository;
import com.clinicturn.api.auth.service.ClinicRoleService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClinicRoleServiceImpl implements ClinicRoleService {

    private final ClinicRoleRepository repository;

    @Override
    @Transactional
    public ClinicRole create(CreateClinicRoleRequest request) {
        validateCodeAlreadyExists(request.getCode());

        ClinicRole role = ClinicRole.builder()
                .code(request.getCode())
                .displayName(request.getCode().getDescription())
                .build();

        return repository.save(role);
    }

    @Override
    public ClinicRole findByCode(ClinicRoleType code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic Role not found with code: " + code.getCode()));
    }

    private void validateCodeAlreadyExists(ClinicRoleType code) {
        if (repository.existsByCode(code)) {
            throw new ResourceAlreadyExistsException("Clinic Role already exists with code: " + code.getCode());
        }
    }
}
