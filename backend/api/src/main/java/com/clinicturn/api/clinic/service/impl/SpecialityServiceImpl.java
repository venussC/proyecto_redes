package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateSpecialityRequest;
import com.clinicturn.api.clinic.dto.response.SpecialityResponse;
import com.clinicturn.api.clinic.exception.InvalidSpecialityCodeException;
import com.clinicturn.api.clinic.model.Speciality;
import com.clinicturn.api.clinic.model.SpecialityType;
import com.clinicturn.api.clinic.repository.SpecialityRepository;
import com.clinicturn.api.clinic.service.SpecialityService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    @Override
    @Transactional
    public SpecialityResponse create(CreateSpecialityRequest request) {
        SpecialityType specialityType = getAndValidateSpecialityTypeFromCode(request.getSpecialityCode());
        validateIntegrity(specialityType);
        Speciality entity = mapFromSpecialityTypetoEntity(specialityType);
        Speciality savedEntity = specialityRepository.save(entity);
        return mapToResponse(savedEntity);
    }

    @Override
    public SpecialityResponse getById(Long id) {
        Speciality entity = validateAndReturnEntityById(id);
        return mapToResponse(entity);
    }

    @Override
    public Speciality getByCodeAndReturnEntity(String code) {
        SpecialityType specialityType = getAndValidateSpecialityTypeFromCode(code);
        return specialityRepository.findByCode(specialityType.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not found with code: " + code));
    }

    @Override
    public List<SpecialityResponse> getAll() {
        return specialityRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SpecialityType getAndValidateSpecialityTypeFromCode(String specialityCode) {
        try {
            return SpecialityType.fromCode(specialityCode);
        } catch (IllegalArgumentException e) {
            throw new InvalidSpecialityCodeException(
                    "Invalid speciality code: " + specialityCode
            );
        }
    }

    private void validateIntegrity(SpecialityType specialityType) {
        assertNotExistsByCode(specialityType.getCode());
        assertNotExistsByDisplayName(specialityType.getDisplayName());
    }

    private void assertNotExistsByCode(String specialityCode) {
        if (specialityRepository.existsByCode(specialityCode)) {
            throw new ResourceAlreadyExistsException("Speciality already exists with code: " + specialityCode);
        }
    }

    private void assertNotExistsByDisplayName(String displayName) {
        if (specialityRepository.existsByDisplayName(displayName)) {
            throw new ResourceAlreadyExistsException("Speciality already exists with name: " + displayName);
        }
    }

    private Speciality validateAndReturnEntityById(Long id) {
        return specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not found with id: " + id));
    }

    private Speciality mapFromSpecialityTypetoEntity(SpecialityType specialityType) {
        return Speciality.builder()
                .code(specialityType.getCode())
                .displayName(specialityType.getDisplayName())
                .build();
    }

    private SpecialityResponse mapToResponse(Speciality entity) {
        return SpecialityResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .displayName(entity.getDisplayName())
                .build();
    }
}
