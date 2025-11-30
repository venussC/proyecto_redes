package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.response.LogoutResponse;

public interface LogoutService {

    LogoutResponse logout(LogoutRequest request);
}
