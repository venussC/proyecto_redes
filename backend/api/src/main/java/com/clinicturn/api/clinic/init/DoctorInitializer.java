package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateDoctorRequest;
import com.clinicturn.api.clinic.model.SpecialityType;
import com.clinicturn.api.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Order(7)
public class DoctorInitializer implements CommandLineRunner {

    private final DoctorService doctorService;

    @Override
    public void run(String... args) throws Exception {
        seedDoctors();
    }

    private void seedDoctors() {
        Map<SpecialityType, List<DoctorSeed>> seeds = Map.of(
                SpecialityType.MED_GENERAL, List.of(
                        new DoctorSeed("Dra. Carolina Paredes", "cparedes@curaepa.com", "6001-2345"),
                        new DoctorSeed("Dr. Manuel Batista", "mbatista@curaepa.com", "6002-3456"),
                        new DoctorSeed("Dr. Jorge Iglesias", "jiglesias@curaepa.com", "6003-4567")
                ),
                SpecialityType.DENTISTA, List.of(
                        new DoctorSeed("Dra. Valeria Villarreal", "vvillarreal@curaepa.com", "6004-5678"),
                        new DoctorSeed("Dr. Ricardo Montenegro", "rmontenegro@curaepa.com", "6005-6789")
                ),
                SpecialityType.OFTALMOLOGIA, List.of(
                        new DoctorSeed("Dr. Álvaro Tejada", "atejada@curaepa.com", "6006-7890"),
                        new DoctorSeed("Dra. Génesis Moreno", "gmoreno@curaepa.com", "6007-8901")
                ),
                SpecialityType.CARDIOLOGIA, List.of(
                        new DoctorSeed("Dr. Fernando Patiño", "fpatino@curaepa.com", "6008-9012"),
                        new DoctorSeed("Dra. Julieta Espino", "jespino@curaepa.com", "6009-0123")
                ),
                SpecialityType.FARMACIA, List.of(
                        new DoctorSeed("Dra. Milagros Fuentes", "mfuentes@curaepa.com", "6010-1234"),
                        new DoctorSeed("Dr. Andrés Castillo", "acastillo@curaepa.com", "6011-2345")
                ),
                SpecialityType.PEDIATRIA, List.of(
                        new DoctorSeed("Dra. Melissa Aparicio", "maparicio@curaepa.com", "6012-3456"),
                        new DoctorSeed("Dr. Darío Quintero", "dquintero@curaepa.com", "6013-4567")
                )
        );

        for (SpecialityType type : SpecialityType.values()) {
            List<DoctorSeed> doctorList = seeds.get(type);
            if (doctorList == null) continue;

            for (DoctorSeed seed : doctorList) {
                try {
                    doctorService.create(
                            CreateDoctorRequest.builder()
                                    .fullName(seed.name())
                                    .specialityCode(type.getCode())
                                    .email(seed.email())
                                    .phoneNumber(seed.phone())
                                    .build()
                    );
                } catch (Exception e) {
                    System.out.println("Doctor already exists or failed to create: " + seed.name());
                }
            }
        }
    }

    private record DoctorSeed(String name, String email, String phone) {}
}
