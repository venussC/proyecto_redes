package com.clinicturn.api.auth.dto.request;

import com.clinicturn.api.auth.model.ClinicRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClinicRoleRequest {

    @NotNull(message = "CreateClinicRole's code should not be null")
    private ClinicRoleType code;
}
