package com.clinicturn.api.patient.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTurnStatusRequest {

    @NotNull(message = "id should not be null")
    Long id;

    @NotBlank(message = "statusName should not be null")
    String statusName;
}
