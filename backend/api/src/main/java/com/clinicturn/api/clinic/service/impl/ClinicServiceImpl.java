package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateClinicRequest;
import com.clinicturn.api.clinic.dto.response.ClinicResponse;
import com.clinicturn.api.clinic.model.Clinic;
import com.clinicturn.api.clinic.repository.ClinicRepository;
import com.clinicturn.api.clinic.service.ClinicService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;

    @Override
    @Transactional
    public ClinicResponse create(CreateClinicRequest request) {
        validateExistsByName(request.getName());
        Clinic entity = mapFromCreateDTOtoEntity(request);
        Clinic savedEntity = clinicRepository.save(entity);
        return mapFromEntityToResponse(savedEntity);
    }

    @Override
    public Clinic findByIdAndReturnEntity(Long id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
    }

    @Override
    public void assertExistsById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic not found with id: " + id);
        }
    }

    private void validateExistsByName(String name) {
        if (clinicRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Clinic already exists with name: " + name);
        }
    }

    private Clinic mapFromCreateDTOtoEntity(CreateClinicRequest request) {
        return Clinic.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    private ClinicResponse mapFromEntityToResponse(Clinic entity) {
        return ClinicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .createdAt(entity.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .updatedAt(entity.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant())
                .build();
    }
}
