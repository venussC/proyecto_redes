package com.clinicturn.api.patient.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTurnDoctorRequest {

    @NotNull(message = "id should not be null")
    Long id;

    @NotNull(message = "doctorId should not be null")
    Long doctorId;
}
