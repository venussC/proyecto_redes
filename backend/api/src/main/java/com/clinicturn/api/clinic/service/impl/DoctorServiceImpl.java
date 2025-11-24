package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateDoctorRequest;
import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.clinic.dto.response.RoomResponse;
import com.clinicturn.api.clinic.dto.response.SpecialityResponse;
import com.clinicturn.api.clinic.model.Doctor;
import com.clinicturn.api.clinic.model.Speciality;
import com.clinicturn.api.clinic.repository.DoctorRepository;
import com.clinicturn.api.clinic.service.DoctorService;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import com.clinicturn.api.clinic.service.SpecialityService;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityService specialityService;
    private final RoomDoctorService roomDoctorService;

    @Override
    @Transactional
    public DoctorResponse create(CreateDoctorRequest request) {
        Speciality speciality = specialityService.getByCodeAndReturnEntity(request.getSpecialityCode());
        Doctor entity = mapFromCreateRequestToEntity(request, speciality);
        Doctor savedEntity = doctorRepository.save(entity);
        return mapToCreateResponse(savedEntity);
    }

    @Override
    public DoctorResponse getById(Long id) {
        Doctor doctor = getAndValidateExistsById(id);
        RoomResponse roomResponse = roomDoctorService.findLatestRoomFromDoctor(doctor.getId());
        return mapToResponse(doctor, roomResponse);
    }

    @Override
    public List<DoctorResponse> getByIsActiveTrue() {
        return doctorRepository.findByIsActiveTrue().stream()
                .map(doctor -> {
                    RoomResponse roomResponse = roomDoctorService.findLatestRoomFromDoctor(doctor.getId());
                    return mapToResponse(doctor, roomResponse);
                })
                .toList();
    }

    @Override
    public List<DoctorResponse> getByIsActiveTrueAndSpecialityCode(String code) {
        return doctorRepository.findByIsActiveTrueAndSpeciality_Code(code).stream()
                .map(doctor -> {
                    RoomResponse roomResponse = roomDoctorService.findLatestRoomFromDoctor(doctor.getId());
                    return mapToResponse(doctor, roomResponse);
                })
                .toList();
    }

    private Doctor getAndValidateExistsById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    private Doctor mapFromCreateRequestToEntity(CreateDoctorRequest request, Speciality speciality) {
        return Doctor.builder()
                .fullName(request.getFullName())
                .speciality(speciality)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    private DoctorResponse mapToCreateResponse(Doctor entity) {
        return DoctorResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .speciality(SpecialityResponse.builder()
                        .id(entity.getSpeciality().getId())
                        .code(entity.getSpeciality().getCode())
                        .displayName(entity.getSpeciality().getDisplayName())
                        .build())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .isActive(entity.getIsActive())
                .createAt(entity.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .updatedAt(entity.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant())
                .room(null)
                .build();
    }

    private DoctorResponse mapToResponse(Doctor entity, RoomResponse roomResponse) {
        return DoctorResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .speciality(SpecialityResponse.builder()
                        .id(entity.getSpeciality().getId())
                        .code(entity.getSpeciality().getCode())
                        .displayName(entity.getSpeciality().getDisplayName())
                        .build())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .isActive(entity.getIsActive())
                .createAt(entity.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .updatedAt(entity.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant())
                .room(roomResponse)
                .build();
    }
}
