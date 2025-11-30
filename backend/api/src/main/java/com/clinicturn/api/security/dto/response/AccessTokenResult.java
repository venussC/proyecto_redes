package com.clinicturn.api.security.dto.response;

import java.time.Instant;

public record AccessTokenResult(
        String token,
        Instant expiresAt
) {}
