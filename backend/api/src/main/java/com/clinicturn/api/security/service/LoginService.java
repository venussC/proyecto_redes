package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);
}
