package com.clinicturn.api.clinic.dto.response;

import lombok.Builder;

@Builder
public record SpecialityResponse(
        Long id,
        String code,
        String displayName
) {}
