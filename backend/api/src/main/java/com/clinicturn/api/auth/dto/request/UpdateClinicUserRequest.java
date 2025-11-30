package com.clinicturn.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateClinicUserRequest {

    @NotNull(message = "UpdateClinicUserRequest's id should not be null")
    private Long id;

    @NotBlank(message = "UpdateClinicUserRequest's username should not be blank")
    @Size(min = 5, max = 50, message = "UpdateClinicUserRequest's username should be between 5 and 50 characters")
    @Pattern(
            regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{5,50}(?<![_.])$",
            message = "Username must only contain letters, numbers, dots or underscores. No beginning or ending with special characters. No repeating of special characters"
    )
    private String username;

    @NotBlank(message = "UpdateClinicUserRequest's phoneNumber should not be blank")
    @Size(min = 9, max = 9, message = "UpdateClinicUserRequest's phoneNumber must be 9 characters long")
    @Pattern(regexp = "^6\\d{3}-\\d{4}$", message = "Phone Number must be a valid Panamanian mobile number")
    private String phoneNumber;
}
