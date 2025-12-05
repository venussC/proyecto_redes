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
                .name("Clinica Curae Panama Oeste")
                .address("Boulevard Costa Verde, La Chorrera, Panama Oeste")
                .phoneNumber("305-3790")
                .latitude(8.89384)
                .longitude(-79.75171)
                .build()
        );

        clinicService.create(CreateClinicRequest.builder()
                .name("Clinica Curae Panama")
                .address("Town Center, Costa Del Este, Panama")
                .phoneNumber("302-8059")
                .latitude(9.0026)
                .longitude(-79.4651)
                .build()
        );

        clinicService.create(CreateClinicRequest.builder()
                .name("Clinica Curae Veraguas")
                .address("Santiago Mall, Santiago, Veraguas")
                .phoneNumber("301-3055")
                .latitude(8.1066)
                .longitude(-80.9490)
                .build()
        );
    }
}
