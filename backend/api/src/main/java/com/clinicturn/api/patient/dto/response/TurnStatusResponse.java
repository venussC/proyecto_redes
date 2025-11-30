package com.clinicturn.api.patient.dto.response;

import lombok.Builder;

@Builder
public record TurnStatusResponse(
        Long id,
        String name
) {}
