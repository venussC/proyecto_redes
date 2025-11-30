package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateClinicRequest;
import com.clinicturn.api.clinic.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class ClinicInitializer implements CommandLineRunner {

    private final ClinicService clinicService;

    @Override
    public void run(String... args) throws Exception {
        clinicService.create(CreateClinicRequest.builder()
                        .name("Clinica Curae")
                        .address("Boulevard Costa Verde, La Chorrera, Panama Oeste")
                        .phoneNumber("305-3790")
                        .latitude(8.89384)
                        .longitude(-79.75171)
                .build()
        );
    }
}
