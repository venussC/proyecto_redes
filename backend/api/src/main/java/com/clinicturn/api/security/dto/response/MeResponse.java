package com.clinicturn.api.security.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MeResponse(
        Long id,
        String username,
        String phoneNumber,
        List<String> roles
){}
