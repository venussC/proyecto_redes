package com.clinicturn.api.patient.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTurnStatusRequest {

    @NotNull(message = "statusCode should not be null")
    private String statusName;
}
