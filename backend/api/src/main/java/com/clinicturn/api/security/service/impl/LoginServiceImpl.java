package com.clinicturn.api.security.service.impl;

import com.clinicturn.api.auth.dto.response.RefreshTokenResult;
import com.clinicturn.api.auth.service.RefreshTokenService;
import com.clinicturn.api.security.adapter.CustomUserDetails;
import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.response.AccessTokenResult;
import com.clinicturn.api.security.dto.response.LoginResponse;
import com.clinicturn.api.security.provider.JwtTokenProvider;
import com.clinicturn.api.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        AccessTokenResult accessTokenResult = jwtTokenProvider.generateAccessToken(user);

        RefreshTokenResult refreshTokenResult = refreshTokenService.create(user);

        return new LoginResponse(
                accessTokenResult.token(),
                accessTokenResult.expiresAt(),
                refreshTokenResult.rawToken()
        );
    }
}
