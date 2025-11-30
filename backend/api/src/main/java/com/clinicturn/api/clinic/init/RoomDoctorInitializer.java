package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateRoomDoctorRequest;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Order(8)
public class RoomDoctorInitializer implements CommandLineRunner {

    private final RoomDoctorService service;

    private static final Map<Long, Long> ASSIGNMENTS = Map.ofEntries(
            // Doctor: Dra. Carolina Paredes
            // Room: 101
            Map.entry(1L, 1L),
            // Doctor: Dr. Manuel Batista
            // Room: 102
            Map.entry(2L, 2L),
            // Doctor: Dr. Jorge Iglesias
            // Room: 103
            Map.entry(3L, 3L),
            // Doctor: Dra. Valeria Villarreal
            // Room: 201
            Map.entry(4L, 4L),
            // Doctor: Dr. Ricardo Montenegro
            // Room: 202
            Map.entry(5L, 5L),
            // Doctor: Dr. Álvaro Tejada
            // Room: 301
            Map.entry(6L, 6L),
            // Doctor: Dra. Génesis Moreno
            // Room: 302
            Map.entry(7L, 7L),
            // Doctor: Dr. Fernando Patiño
            // Room: 401
            Map.entry(8L, 8L),
            // Doctor: Dra. Julieta Espino
            // Room: 402
            Map.entry(9L, 9L),
            // Doctor: Dra. Milagros Fuentes
            // Room: 501
            Map.entry(10L, 10L),
            // Doctor: Dr. Andrés Castillo
            // Room: 502
            Map.entry(11L, 11L),
            // Doctor: Dra. Melissa Aparicio
            // Room: 601
            Map.entry(12L, 12L),
            // Doctor: Dr. Darío Quintero
            // Room: 602
            Map.entry(13L, 13L)
    );

    @Override
    public void run(String... args) throws Exception {
        ASSIGNMENTS.forEach((doctorId, roomId) -> {
            try {
                service.assignRoom(CreateRoomDoctorRequest.builder()
                        .doctorId(doctorId)
                        .roomId(roomId)
                        .build()
                );
            } catch (Exception e) {
                System.out.println("❌ Error assigning room " + roomId +
                        " to doctor " + doctorId + ": " + e.getMessage());
            }
        });
    }
}
