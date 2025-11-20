package com.clinicturn.api.auth.service;

import com.clinicturn.api.auth.dto.response.RefreshTokenResult;
import com.clinicturn.api.auth.model.RefreshToken;
import com.clinicturn.api.security.adapter.CustomUserDetails;

public interface RefreshTokenService {

    RefreshTokenResult create(CustomUserDetails userDetails);

    void revoke(String rawToken);

    RefreshToken verifyAndGet(String rawToken);
}