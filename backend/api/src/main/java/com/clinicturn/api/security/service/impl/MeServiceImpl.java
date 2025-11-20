package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.auth.service.RefreshTokenService;
import com.clinicturn.api.security.adapter.CustomUserDetails;
import com.clinicturn.api.security.dto.response.MeResponse;
import com.clinicturn.api.security.service.MeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeServiceImpl implements MeService {

    private final RefreshTokenService refreshTokenService;
    private final ClinicUserService userService;
    private final ClinicRoleUserService roleUserService;

    @Override
    public MeResponse me(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        ClinicUser userEntity = userService.findByIdAndReturnEntity(userDetails.getId());
        List<ClinicRoleType> roleCodes = roleUserService.findRoleCodesByUserId(userEntity.getId());
        return MeResponse.builder()
                .id(userDetails.getId())
                .username(userEntity.getUsername())
                .phoneNumber(userEntity.getPhoneNumber())
                .roles(roleCodes.stream()
                        .map(ClinicRoleType::getCode)
                        .toList()
                )
                .build();
    }
}
