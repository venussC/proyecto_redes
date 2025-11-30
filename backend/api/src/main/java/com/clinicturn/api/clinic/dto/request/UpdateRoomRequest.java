package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRoomRequest {

    @NotNull(message = "id should not be null")
    Long id;

    @NotNull(message = "isActive should not be null")
    Boolean isActive;

    @NotNull(message = "isAvailable should not be null")
    Boolean isAvailable;
}
