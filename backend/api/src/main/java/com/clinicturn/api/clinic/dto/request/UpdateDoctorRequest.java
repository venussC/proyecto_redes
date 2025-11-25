package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDoctorRequest {

    @NotNull(message = "UpdateDoctorRequest's id should not be null")
    Long id;

    @NotBlank(message = "UpdateDoctorRequest's specialityCode should not be blank")
    private String specialityCode;

    @Email(message = "UpdateDoctorRequest's email should be a valid email")
    @NotBlank(message = "UpdateDoctorRequest's should not be blank")
    private String email;

    @NotBlank(message = "UpdateDoctorRequest's phoneNumber should not be blank")
    @Size(max = 10, message = "phoneNumber should be 10 characters at max")
    @Pattern(
            regexp = "^(?:\\d{3}-\\d{4}|\\d{4}-\\d{4})$",
            message = "Invalid phone number format (use XXX-XXXX or XXXX-XXXX)"
    )
    private String phoneNumber;

    @NotNull(message = "UpdateDoctorRequest's isActive should not be null")
    private Boolean isActive;
}
