package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.request.RegisterRequest;
import com.clinicturn.api.security.dto.response.*;
import org.springframework.security.core.Authentication;

public interface AuthManagerService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    RefreshResponse refresh(RefreshRequest request);

    LogoutResponse logout(LogoutRequest request);

    MeResponse me(Authentication authentication);
}
