package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;
import com.clinicturn.api.security.dto.response.RefreshResponse;
import com.clinicturn.api.security.service.AuthManagerService;
import com.clinicturn.api.security.service.LoginService;
import com.clinicturn.api.security.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManagerServiceImpl implements AuthManagerService {

    private final LoginService loginService;
    private final RefreshService refreshService;

    @Override
    public LoginResponse login(LoginRequest request) {
        return loginService.login(request);
    }

    @Override
    public RefreshResponse refresh(RefreshRequest request) {
        return refreshService.refresh(request);
    }

    @Override
    public void logout(LogoutRequest request) {

    }
}
