package com.clinicturn.api.clinic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoomDoctorRequest {

    @NotNull(message = "CreateRoomDoctorRequest's doctorId should not be null")
    private Long doctorId;

    @NotNull(message = "CreateRoomDoctorRequest's roomId should not be null")
    private Long roomId;
}
