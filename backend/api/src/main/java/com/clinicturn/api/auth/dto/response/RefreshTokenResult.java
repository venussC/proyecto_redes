package com.clinicturn.api.auth.dto.response;

import com.clinicturn.api.auth.model.RefreshToken;

public record RefreshTokenResult (
        String rawToken,
        RefreshToken entity
){}
