package com.clinicturn.api.auth.service;

import com.clinicturn.api.auth.dto.response.RefreshTokenResult;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.model.RefreshToken;

public interface RefreshTokenService {

    RefreshTokenResult create(ClinicUser user);

    void revoke(String rawToken);

    RefreshToken verifyAndGet(String rawToken);
}