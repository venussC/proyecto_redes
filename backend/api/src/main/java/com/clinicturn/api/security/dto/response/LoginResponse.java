package com.clinicturn.api.security.dto.response;

import java.time.Instant;

public record LoginResponse(
        String accessToken,
        Instant expiresAt,
        String refreshToken
) {}
