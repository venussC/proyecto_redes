package com.clinicturn.api.auth.init;

import com.clinicturn.api.auth.dto.request.CreateClinicRoleUserRequest;
import com.clinicturn.api.auth.dto.request.CreateClinicUserRequest;
import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.auth.service.ClinicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class ClinicUserInitializer implements CommandLineRunner {

    private final ClinicUserService userService;
    private final ClinicRoleUserService roleUserService;

    @Override
    public void run(String... args) throws Exception {
        createTestAdminUser();
        createTestDoctorUser();
        createTestPatientUser();
        createTestReceptionUser();
    }

    private void createTestAdminUser() {
        ClinicUser testUser = userService.createAndReturnEntity(CreateClinicUserRequest.builder()
                .username("TestAdminUser01")
                .password("TestPasswordClinic2025*")
                .phoneNumber("6666-6666")
                .build()
        );

        roleUserService.create(CreateClinicRoleUserRequest.builder()
                .code(ClinicRoleType.ADMIN)
                .username("TestAdminUser01")
                .build()
        );
    }

    private void createTestDoctorUser() {
        userService.createAndReturnEntity(CreateClinicUserRequest.builder()
                .username("TestDoctorUser01")
                .password("TestPasswordClinic2025*")
                .phoneNumber("6666-6666")
                .build()
        );

        roleUserService.create(CreateClinicRoleUserRequest.builder()
                .code(ClinicRoleType.DOCTOR)
                .username("TestDoctorUser01")
                .build()
        );
    }

    private void createTestPatientUser() {
        userService.createAndReturnEntity(CreateClinicUserRequest.builder()
                .username("TestPatientUser01")
                .password("TestPasswordClinic2025*")
                .phoneNumber("6666-6666")
                .build()
        );

        roleUserService.create(CreateClinicRoleUserRequest.builder()
                .code(ClinicRoleType.PATIENT)
                .username("TestPatientUser01")
                .build()
        );
    }

    private void createTestReceptionUser() {
        userService.createAndReturnEntity(CreateClinicUserRequest.builder()
                .username("TestReceptionUser01")
                .password("TestPasswordClinic2025*")
                .phoneNumber("6666-6666")
                .build()
        );

        roleUserService.create(CreateClinicRoleUserRequest.builder()
                .code(ClinicRoleType.RECEPTION)
                .username("TestReceptionUser01")
                .build()
        );
    }
}
