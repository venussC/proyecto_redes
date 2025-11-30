package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.dto.response.RefreshTokenResult;
import com.clinicturn.api.auth.model.RefreshToken;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.auth.service.RefreshTokenService;
import com.clinicturn.api.security.adapter.CustomUserDetails;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.AccessTokenResult;
import com.clinicturn.api.security.dto.response.RefreshResponse;
import com.clinicturn.api.security.provider.JwtTokenProvider;
import com.clinicturn.api.security.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshServiceImpl implements RefreshService {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public RefreshResponse refresh(RefreshRequest request) {
        RefreshToken oldToken = refreshTokenService.verifyAndGet(request.getRefreshToken());

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(oldToken.getUser().getUsername());

        refreshTokenService.revoke(request.getRefreshToken());

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        RefreshTokenResult newRefreshTokenResult = refreshTokenService.create(customUserDetails);

        AccessTokenResult newAccessTokenResult = jwtTokenProvider.generateAccessToken(customUserDetails);

        return new RefreshResponse(
                newAccessTokenResult.token(),
                newAccessTokenResult.expiresAt(),
                newRefreshTokenResult.rawToken()
        );
    }
}
