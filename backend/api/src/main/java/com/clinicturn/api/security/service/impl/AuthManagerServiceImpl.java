package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;
import com.clinicturn.api.security.dto.response.LogoutResponse;
import com.clinicturn.api.security.dto.response.MeResponse;
import com.clinicturn.api.security.dto.response.RefreshResponse;
import com.clinicturn.api.security.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManagerServiceImpl implements AuthManagerService {

    private final LoginService loginService;
    private final RefreshService refreshService;
    private final LogoutService logoutService;
    private final MeService meService;

    @Override
    public LoginResponse login(LoginRequest request) {
        return loginService.login(request);
    }

    @Override
    public RefreshResponse refresh(RefreshRequest request) {
        return refreshService.refresh(request);
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        return logoutService.logout(request);
    }

    @Override
    public MeResponse me(Authentication authentication) {
        return meService.me(authentication);
    }
}
