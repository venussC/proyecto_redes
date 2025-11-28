package com.clinicturn.api.patient.service.impl;

import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import com.clinicturn.api.patient.dto.request.CreateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.TurnStatusResponse;
import com.clinicturn.api.patient.exception.InvalidStatusNameException;
import com.clinicturn.api.patient.model.StatusType;
import com.clinicturn.api.patient.model.TurnStatus;
import com.clinicturn.api.patient.repository.TurnStatusRepository;
import com.clinicturn.api.patient.service.TurnStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TurnStatusServiceImpl implements TurnStatusService {

    private final TurnStatusRepository repository;

    @Override
    @Transactional
    public TurnStatusResponse create(CreateTurnStatusRequest request) {
        StatusType statusType = getAndValidateStatusTypeFromName(request.getStatusName());
        validateIntegrity(statusType.name());
        TurnStatus entity = mapFromStatusTypeToEntity(statusType);
        TurnStatus saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public TurnStatusResponse getById(Long id) {
        TurnStatus entity = validateAndReturnById(id);
        return mapToResponse(entity);
    }

    @Override
    public TurnStatusResponse getByName(String name) {
        TurnStatus entity = validateAndReturnByName(name);
        return mapToResponse(entity);
    }

    @Override
    public List<TurnStatusResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private StatusType getAndValidateStatusTypeFromName(String statusName) {
        try {
            return StatusType.fromName(statusName);
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusNameException("Invalid Status Name: " + statusName);
        }
    }

    private void validateIntegrity(String statusName) {
        if (repository.existsByName(statusName)) {
            throw new ResourceAlreadyExistsException("Turn Status already exists with name: " + statusName);
        }
    }

    private TurnStatus validateAndReturnById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turn Status not found with id: " + id));
    }

    private TurnStatus validateAndReturnByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Turn Status not found with name: " + name));
    }

    private TurnStatus mapFromStatusTypeToEntity(StatusType statusType) {
        return TurnStatus.builder()
                .name(statusType.name())
                .build();
    }

    private TurnStatusResponse mapToResponse(TurnStatus entity) {
        return TurnStatusResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
