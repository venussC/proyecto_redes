package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleUserRequest;
import com.clinicturn.api.auth.dto.request.CreateClinicUserRequest;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.security.dto.request.RegisterRequest;
import com.clinicturn.api.security.dto.response.RegisterResponse;
import com.clinicturn.api.security.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private final ClinicUserService userService;
    private final ClinicRoleUserService roleUserService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        CreateClinicUserRequest createDto = mapFromRequestToCreateDTO(request);
        userService.createAndReturnEntity(createDto);
        roleUserService.create(CreateClinicRoleUserRequest.builder()
                .code(ClinicRoleType.PATIENT)
                .username(createDto.getUsername())
                .build()
        );
        return new RegisterResponse("User registered successfully");
    }

    private CreateClinicUserRequest mapFromRequestToCreateDTO(RegisterRequest request) {
        return CreateClinicUserRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
