package com.clinicturn.api.auth.service.impl;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleUserRequest;
import com.clinicturn.api.auth.exception.EmptyRolesException;
import com.clinicturn.api.auth.model.ClinicRole;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicRoleUser;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.repository.ClinicRoleUserRepository;
import com.clinicturn.api.auth.service.ClinicRoleService;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ClinicRoleType> findRoleCodesByUserId(Long userId) {
        List<ClinicRoleType> codes = repository.findRoleCodesByUserId(userId);
        validateUserRolesNotEmpty(codes, userId);
        return codes;
    }

    private void validateRoleUserAlreadyExists(Long roleId, Long userId) {
        if (existsByRoleIdAndUserId(roleId, userId)) {
            throw new ResourceAlreadyExistsException("Clinic Role User already exists for Role Id: " + roleId + "and User Id: " + userId);
        }
    }

    private void validateUserRolesNotEmpty(List<ClinicRoleType> codes, Long userId) {
        if (codes.isEmpty()) {
            throw new EmptyRolesException("User with id " + userId + " has no assigned roles");
        }
    }
}
