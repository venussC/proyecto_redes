package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateRoomRequest;
import com.clinicturn.api.clinic.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(5)
public class RoomInitializer implements CommandLineRunner {

    private final RoomService roomService;

    @Override
    public void run(String... args) {

        Long clinicId = 1L;

        List<Integer> roomNumbers = List.of(101, 102, 103, 201, 202, 301, 302, 401, 402,
                501, 502, 601, 602);

        roomNumbers.forEach(roomNumber -> {
            CreateRoomRequest request = CreateRoomRequest.builder()
                    .clinicId(clinicId)
                    .roomNumber(roomNumber)
                    .build();

            try {
                roomService.create(request);
            } catch (Exception ignored) {

            }
        });
    }
}
