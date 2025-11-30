package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.security.adapter.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClinicUserService clinicUserService;
    private final ClinicRoleUserService clinicRoleUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClinicUser userEntity = clinicUserService.findByUsernameAndReturnEntity(username);

        List<ClinicRoleType> roleCodes = clinicRoleUserService.findRoleCodesByUserId(userEntity.getId());

        List<SimpleGrantedAuthority> authorities = mapClinicRoleTypeToGrantedAuthorities(roleCodes);

        return CustomUserDetails.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPasswordHash())
                .authorities(authorities)
                .enabled(userEntity.getEnabled())
                .accountNonLocked(userEntity.getAccountNonLocked())
                .build();
    }

    private List<SimpleGrantedAuthority> mapClinicRoleTypeToGrantedAuthorities(List<ClinicRoleType> roleCodes) {
        return roleCodes.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }
}
