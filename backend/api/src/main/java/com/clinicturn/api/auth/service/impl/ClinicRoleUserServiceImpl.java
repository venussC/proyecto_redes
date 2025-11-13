package com.clinicturn.api.auth.service.impl;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleUserRequest;
import com.clinicturn.api.auth.model.ClinicRole;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicRoleUser;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.repository.ClinicRoleUserRepository;
import com.clinicturn.api.auth.service.ClinicRoleService;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClinicRoleUserServiceImpl implements ClinicRoleUserService {

    private final ClinicRoleUserRepository repository;
    private final ClinicRoleService roleService;
    private final ClinicUserService userService;

    @Override
    @Transactional
    public ClinicRoleUser create(CreateClinicRoleUserRequest request) {
        ClinicRole roleEntity = roleService.findByCode(request.getCode());
        ClinicUser userEntity = userService.findByUsernameAndReturnEntity(request.getUsername());
        validateRoleUserAlreadyExists(roleEntity.getId(), userEntity.getId());
        ClinicRoleUser roleUserEntity = ClinicRoleUser.builder()
                .role(roleEntity)
                .user(userEntity)
                .build();
        return repository.save(roleUserEntity);
    }

    @Override
    public boolean existsByRoleIdAndUserId(Long roleId, Long userId) {
        return repository.existsByRole_IdAndUser_Id(roleId, userId);
    }

    @Override
    public boolean existsByRoleTypeCodeAndUsername(ClinicRoleType code, String username) {
        return false;
    }

    private void validateRoleUserAlreadyExists(Long roleId, Long userId) {
        if (existsByRoleIdAndUserId(roleId, userId)) {
            throw new RuntimeException("Clinic Role User already exists for Role Id: " + roleId + "and User Id: " + userId);
        }
    }
}
