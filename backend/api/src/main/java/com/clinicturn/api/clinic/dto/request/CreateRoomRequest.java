package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoomRequest {

    @NotNull(message = "CreateRoomRequest's clinicId should not be null")
    private Long clinicId;

    @NotNull(message = "CreateRoomRequest's roomNumber should not be null")
    private Integer roomNumber;
}
