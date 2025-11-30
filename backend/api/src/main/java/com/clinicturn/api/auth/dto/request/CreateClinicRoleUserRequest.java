package com.clinicturn.api.auth.dto.request;

import com.clinicturn.api.auth.model.ClinicRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClinicRoleUserRequest {

    @NotNull(message = "CreateRoleUserRequest's code should not be null")
    private ClinicRoleType code;

    @NotBlank(message = "CreateRoleUserRequest's code should not be null")
    private String username;
}
