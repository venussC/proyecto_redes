package com.clinicturn.api.security.dto.response;

import java.time.Instant;

public record RefreshResponse(
        String accessToken,
        Instant expiresAt,
        String refreshToken
) {}
