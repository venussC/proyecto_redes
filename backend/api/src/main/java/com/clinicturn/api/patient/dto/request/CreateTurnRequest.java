package com.clinicturn.api.patient.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTurnRequest {

    private Long specialityId;

    private Long patientId;

    private String reason;
}