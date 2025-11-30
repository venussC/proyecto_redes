package com.clinicturn.api.patient.service.impl;

import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicRoleUser;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.service.ClinicRoleUserService;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import com.clinicturn.api.patient.dto.request.CreatePatientRequest;
import com.clinicturn.api.patient.dto.response.PatientResponse;
import com.clinicturn.api.patient.exception.InvalidStateException;
import com.clinicturn.api.patient.model.Patient;
import com.clinicturn.api.patient.repository.PatientRepository;
import com.clinicturn.api.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRoleUserService clinicRoleUserService;

    @Override
    public PatientResponse getMe(String username) {
        ClinicRoleUser roleUserEntity = clinicRoleUserService.findByUserUsername(username);
        ClinicRoleType roleType = roleUserEntity.getRole().getCode();
        return switch (roleType) {
            case PATIENT -> mapToResponse(findByUserUsernameAndReturnEntity(username));
            case RECEPTION -> buildEmptyResponse();
            default -> throw new InvalidStateException(
                    "Role type " + roleType + " cannot find a patient"
            );
        };
    }

    @Override
    @Transactional
    public PatientResponse create(CreatePatientRequest request, String username) {
        ClinicRoleUser roleUserEntity = clinicRoleUserService.findByUserUsername(username);
        ClinicUser userEntity = roleUserEntity.getUser();
        ClinicRoleType roleType = roleUserEntity.getRole().getCode();
        Patient patient = switch (roleType) {
            case PATIENT -> validatePatient(request, userEntity);
            case RECEPTION -> savePatient(request, userEntity);
            default -> throw new InvalidStateException(
                    "Role type " + roleType + " cannot create or register a patient"
            );
        };
        return mapToResponse(patient);
    }

    @Override
    public Patient getByIdAndReturnEntity(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    private Patient validatePatient(CreatePatientRequest request, ClinicUser userEntity) {
        if (!patientRepository.existsByUser_Id(userEntity.getId())) {
            return savePatient(request, userEntity);
        } else {
            return findByUserIdAndReturnEntity(userEntity.getId());
        }
    }

    private Patient savePatient(CreatePatientRequest request, ClinicUser userEntity) {
        Patient patient = Patient.builder()
                .user(userEntity)
                .fullName(request.getFullName())
                .dni(request.getDni())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return patientRepository.save(patient);
    }

    private Patient findByUserIdAndReturnEntity(Long userId) {
        return patientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with user id: " + userId));
    }

    private Patient findByUserUsernameAndReturnEntity(String username) {
        return patientRepository.findByUser_Username(username)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with username: " + username));
    }

    private PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .userId(patient.getId())
                .fullName(patient.getFullName())
                .dni(patient.getDni())
                .phoneNumber(patient.getPhoneNumber())
                .createdAt(patient.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .build();
    }

    private PatientResponse buildEmptyResponse() {
        return PatientResponse.builder()
                .id(null)
                .userId(null)
                .fullName("")
                .dni("")
                .phoneNumber("")
                .createdAt(null)
                .build();
    }
}
