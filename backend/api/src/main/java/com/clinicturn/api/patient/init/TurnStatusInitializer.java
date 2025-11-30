package com.clinicturn.api.patient.init;

import com.clinicturn.api.patient.dto.request.CreateTurnStatusRequest;
import com.clinicturn.api.patient.model.StatusType;
import com.clinicturn.api.patient.service.TurnStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(9)
public class TurnStatusInitializer implements CommandLineRunner {

    private final TurnStatusService service;

    @Override
    public void run(String... args) throws Exception {
        for (StatusType type : StatusType.values()) {
            try {
                CreateTurnStatusRequest request = CreateTurnStatusRequest.builder()
                        .statusName(type.name())
                        .build();
                service.create(request);
            } catch (Exception e) {
                System.out.println("Skipping existing status: " + type.name());
            }
        }
    }
}
