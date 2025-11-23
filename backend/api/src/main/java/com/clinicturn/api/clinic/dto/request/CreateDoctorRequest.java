package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDoctorRequest {

    @NotBlank(message = "CreateDoctorRequest's full name should not be blank")
    @Size(max = 300, message = "fullName should be 300 characters at max")
    private String fullName;

    @NotBlank(message = "CreateDoctorRequest's specialityCode should not be blank")
    private String specialityCode;

    @Email(message = "CreateDoctorRequest's email should be a valid email")
    private String email;

    @NotBlank(message = "CreateDoctorRequest's phoneNumber should not be blank")
    @Size(max = 10, message = "phoneNumber should be 10 characters at max")
    @Pattern(
            regexp = "^(?:\\d{3}-\\d{4}|\\d{4}-\\d{4})$",
            message = "Invalid phone number format (use XXX-XXXX or XXXX-XXXX)"
    )
    private String phoneNumber;
}
