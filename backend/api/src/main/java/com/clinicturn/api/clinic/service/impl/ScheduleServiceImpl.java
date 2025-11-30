package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateScheduleRequest;
import com.clinicturn.api.clinic.dto.response.ClinicScheduleResponse;
import com.clinicturn.api.clinic.dto.response.ScheduleResponse;
import com.clinicturn.api.clinic.model.Clinic;
import com.clinicturn.api.clinic.model.Schedule;
import com.clinicturn.api.clinic.repository.ScheduleRepository;
import com.clinicturn.api.clinic.service.ClinicService;
import com.clinicturn.api.clinic.service.ScheduleService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClinicService clinicService;

    @Override
    @Transactional
    public ScheduleResponse create(CreateScheduleRequest request) {
        Clinic clinicEntity = getAndValidateClinicById(request.getClinicId());
        validateNotExistsByClinicAndWeekDay(request.getClinicId(), request.getWeekDay());
        Schedule schedule = mapFromCreateDTOToEntity(request, clinicEntity);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return mapFromEntityToResponse(savedSchedule);
    }

    @Override
    public ClinicScheduleResponse getSchedulesFromClinic(Long clinicId) {
        Clinic clinicEntity = clinicService.findByIdAndReturnEntity(clinicId);
        List<Schedule> schedules = scheduleRepository.findOrderedByClinic(clinicId);
        List<ClinicScheduleResponse.ScheduleInfo> infoList = mapToScheduleInfoList(schedules);
        return new ClinicScheduleResponse(
                clinicEntity.getId(),
                clinicEntity.getName(),
                infoList
        );
    }

    private Clinic getAndValidateClinicById(Long clinicId) {
        return clinicService.findByIdAndReturnEntity(clinicId);
    }

    private void validateNotExistsByClinicAndWeekDay(Long clinicId, DayOfWeek weekDay) {
        if (scheduleRepository.existsByClinicIdAndWeekDay(clinicId, weekDay)) {
            throw new ResourceAlreadyExistsException(
                    "Schedule already exists for clinic id: " + clinicId +
                            " on day " + weekDay
            );
        }
    }

    private Schedule mapFromCreateDTOToEntity(CreateScheduleRequest request, Clinic clinicEntity) {
        return Schedule.builder()
                .clinic(clinicEntity)
                .weekDay(request.getWeekDay())
                .opening(request.getOpening() != null ? request.getOpening() : null)
                .closing(request.getClosing() != null ? request.getClosing() : null)
                .isClosed(request.getIsClosed() != null ? request.getIsClosed() : false)
                .build();
    }

    private ScheduleResponse mapFromEntityToResponse(Schedule savedSchedule) {
        return ScheduleResponse.builder()
                .id(savedSchedule.getId())
                .clinicId(savedSchedule.getClinic().getId())
                .weekDay(savedSchedule.getWeekDay().toString())
                .opening(savedSchedule.getOpening())
                .closing(savedSchedule.getClosing())
                .isClosed(savedSchedule.getIsClosed())
                .build();
    }

    private List<ClinicScheduleResponse.ScheduleInfo> mapToScheduleInfoList(List<Schedule> schedules) {
        return schedules.stream()
                .map(s -> new ClinicScheduleResponse.ScheduleInfo(
                        s.getWeekDay().toString(),
                        s.getOpening(),
                        s.getClosing(),
                        s.getIsClosed()
                ))
                .toList();
    }
}
