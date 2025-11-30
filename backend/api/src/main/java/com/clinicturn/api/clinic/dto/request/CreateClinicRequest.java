package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClinicRequest {

    @NotBlank(message = "CreateClinicRequest's name should not be blank")
    @Size(max = 200, message = "name should be 200 characters at max")
    private String name;

    @NotBlank(message = "CreateClinicRequest's address should not be blank")
    @Size(max = 350, message = "address should be 350 characters at max")
    private String address;

    @NotBlank(message = "CreateClinicRequest's phoneNumber should not be blank")
    @Size(max = 10, message = "phoneNumber should be 10 characters at max")
    @Pattern(
            regexp = "^(?:\\d{3}-\\d{4}|\\d{4}-\\d{4})$",
            message = "Invalid phone number format (use XXX-XXXX or XXXX-XXXX)"
    )
    private String phoneNumber;

    @NotNull(message = "CreateClinicRequest's latitude should not be null")
    private Double latitude;

    @NotNull(message = "CreateClinicRequest's longitude should not be null")
    private Double longitude;
}
