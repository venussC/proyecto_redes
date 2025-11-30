package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSpecialityRequest {

    @NotNull(message = "CreateSpecialityRequest's should not be null")
    private String specialityCode;
}
