package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;
import com.clinicturn.api.security.dto.response.LogoutResponse;
import com.clinicturn.api.security.dto.response.RefreshResponse;

public interface AuthManagerService {

    LoginResponse login(LoginRequest request);

    RefreshResponse refresh(RefreshRequest request);

    LogoutResponse logout(LogoutRequest request);
}
