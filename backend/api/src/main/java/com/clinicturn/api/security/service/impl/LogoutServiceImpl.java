package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.service.RefreshTokenService;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.response.LogoutResponse;
import com.clinicturn.api.security.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final RefreshTokenService refreshTokenService;

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        refreshTokenService.verifyAndRevoke(request.getRefreshToken());
        return new LogoutResponse(
                "Logout Successfully"
        );
    }
}
