package com.clinicturn.api.clinic.init;

import com.clinicturn.api.clinic.dto.request.CreateScheduleRequest;
import com.clinicturn.api.clinic.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Order(4)
public class ScheduleInitializer implements CommandLineRunner {

    private final ScheduleService scheduleService;

    @Override
    public void run(String... args) throws Exception {

        Long clinicId = 1L;

        for (DayOfWeek day : DayOfWeek.values()) {

            LocalTime opening;
            LocalTime closing;
            boolean isClosed;

            switch (day) {
                case SATURDAY -> {
                    opening = LocalTime.of(8, 0);
                    closing = LocalTime.of(14, 0);
                    isClosed = false;
                }
                case SUNDAY -> {
                    opening = null;
                    closing = null;
                    isClosed = true;
                }
                default -> {
                    opening = LocalTime.of(8, 0);
                    closing = LocalTime.of(20, 0);
                    isClosed = false;
                }
            }

            CreateScheduleRequest request = CreateScheduleRequest.builder()
                    .clinicId(clinicId)
                    .weekDay(day)
                    .opening(opening)
                    .closing(closing)
                    .isClosed(isClosed)
                    .build();

            scheduleService.create(request);
        }
    }
}
