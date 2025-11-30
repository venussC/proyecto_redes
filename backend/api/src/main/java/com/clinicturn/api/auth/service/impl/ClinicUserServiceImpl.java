package com.clinicturn.api.auth.service.impl;

import com.clinicturn.api.auth.dto.request.CreateClinicUserRequest;
import com.clinicturn.api.auth.dto.request.UpdateClinicUserRequest;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.repository.ClinicUserRepository;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.common.exception.IdsMismatchException;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClinicUserServiceImpl implements ClinicUserService {

    private final ClinicUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ClinicUser createAndReturnEntity(CreateClinicUserRequest request) {
        validateUsernameAlreadyExists(request.getUsername());

        ClinicUser user = ClinicUser.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .build();

        return repository.save(user);
    }

    @Override
    @Transactional
    public ClinicUser updateAndReturnEntity(Long pathId, UpdateClinicUserRequest request) {
        ClinicUser userEntity = findByIdAndReturnEntity(pathId);

        validateMatchingIds(pathId, request.getId());

        if (!userEntity.getUsername().equals(request.getUsername())) {
            validateUsernameAlreadyExists(request.getUsername());
        }

        userEntity.setUsername(request.getUsername());
        userEntity.setPhoneNumber(request.getPhoneNumber());

        return repository.save(userEntity);
    }

    @Override
    public ClinicUser findByIdAndReturnEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic User not found with id: " + id));
    }

    @Override
    public ClinicUser findByUsernameAndReturnEntity(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic User not found with username: " + username));
    }

    private void validateUsernameAlreadyExists(String username) {
        if (repository.existsByUsername(username)) {
            throw new ResourceAlreadyExistsException("Clinic User already exists with username: " + username);
        }
    }

    private void validateMatchingIds(Long pathId, Long requestId) {
        if (!pathId.equals(requestId)) {
            throw new IdsMismatchException(("Clinic User's id and Path's id doesn't match."));
        }
    }
}
