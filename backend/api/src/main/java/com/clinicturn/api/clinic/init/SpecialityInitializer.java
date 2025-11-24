package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateSpecialityRequest;
import com.clinicturn.api.clinic.model.SpecialityType;
import com.clinicturn.api.clinic.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(6)
public class SpecialityInitializer implements CommandLineRunner {

    private final SpecialityService specialityService;

    @Override
    public void run(String... args) throws Exception {

        for (SpecialityType type : SpecialityType.values()) {

            CreateSpecialityRequest request = CreateSpecialityRequest.builder()
                    .specialityCode(type.getCode())
                    .build();

            try {
                specialityService.create(request);
            } catch (Exception ignored) {

            }
        }
    }
}
