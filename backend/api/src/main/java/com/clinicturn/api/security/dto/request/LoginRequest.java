package com.clinicturn.api.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @NotBlank(message = "LoginRequest's username should not be blank")
    @Size(min = 5, max = 50, message = "LoginRequest's username should be between 5 and 50 characters")
    @Pattern(
            regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{5,50}(?<![_.])$",
            message = "Username must only contain letters, numbers, dots or underscores. No beginning or ending with special characters. No repeating of special characters"
    )
    private String username;

    @NotBlank(message = "LoginRequest's password should not be blank")
    @Size(min = 10, max = 128, message = "LoginRequest's password should be between 10 and 128 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,128}$",
            message = "Password must contain at least one uppercase, one lowercase, one number and one special character"
    )
    private String password;
}
