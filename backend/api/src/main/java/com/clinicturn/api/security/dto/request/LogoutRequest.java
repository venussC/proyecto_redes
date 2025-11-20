package com.clinicturn.api.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LogoutRequest {

    @NotBlank(message = "LogoutRequest's refreshToken should not be blank")
    private String refreshToken;
}
