package com.clinicturn.api.patient.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePatientRequest {

    @NotBlank(message = "fullName should not be blank")
    @Size(max = 300, message = "fullName should be 300 characters at max")
    private String fullName;

    @NotBlank(message = "dni should not be blank")
    @Size(max = 20, message = "dni should be 20 characters at max")
    private String dni;

    @NotBlank(message = "phoneNumber should not be blank")
    @Size(max = 9, message = "phoneNumber should be 9 characters at max")
    @Pattern(
            regexp = "^(?:\\d{3}-\\d{4}|\\d{4}-\\d{4})$",
            message = "Invalid phone number format (use XXX-XXXX or XXXX-XXXX)"
    )
    private String phoneNumber;
}
