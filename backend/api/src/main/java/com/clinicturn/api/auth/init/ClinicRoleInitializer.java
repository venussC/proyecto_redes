package com.clinicturn.api.auth.init;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleRequest;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.service.ClinicRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class ClinicRoleInitializer implements CommandLineRunner {

    private final ClinicRoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        createAdminRole();
        createDoctorRole();
        createPatientRole();
        createReceptionRole();
    }

    private void createAdminRole() {
        roleService.create(CreateClinicRoleRequest.builder()
                .code(ClinicRoleType.ADMIN)
                .build()
        );
    }

    private void createDoctorRole() {
        roleService.create(CreateClinicRoleRequest.builder()
                .code(ClinicRoleType.DOCTOR)
                .build()
        );
    }

    private void createPatientRole() {
        roleService.create(CreateClinicRoleRequest.builder()
                .code(ClinicRoleType.PATIENT)
                .build()
        );
    }

    private void createReceptionRole() {
        roleService.create(CreateClinicRoleRequest.builder()
                .code(ClinicRoleType.RECEPTION)
                .build()
        );
    }
}
