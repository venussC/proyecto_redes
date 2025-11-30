package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.RegisterRequest;
import com.clinicturn.api.security.dto.response.RegisterResponse;

public interface RegisterService {

    RegisterResponse register(RegisterRequest request);
}
