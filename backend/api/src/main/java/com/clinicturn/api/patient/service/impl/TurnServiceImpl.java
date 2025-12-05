package com.clinicturn.api.patient.service.impl;

import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.clinic.dto.response.DoctorResponse;
import com.clinicturn.api.clinic.model.Doctor;
import com.clinicturn.api.clinic.model.Speciality;
import com.clinicturn.api.clinic.service.DoctorService;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import com.clinicturn.api.clinic.service.SpecialityService;
import com.clinicturn.api.common.exception.IdsMismatchException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import com.clinicturn.api.patient.dto.request.CreateTurnRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnDoctorRequest;
import com.clinicturn.api.patient.dto.request.UpdateTurnStatusRequest;
import com.clinicturn.api.patient.dto.response.PacientCountResponse;
import com.clinicturn.api.patient.dto.response.TurnCountResponse;
import com.clinicturn.api.patient.dto.response.TurnResponse;
import com.clinicturn.api.patient.exception.TurnStillActiveException;
import com.clinicturn.api.patient.model.Patient;
import com.clinicturn.api.patient.model.StatusType;
import com.clinicturn.api.patient.model.Turn;
import com.clinicturn.api.patient.model.TurnStatus;
import com.clinicturn.api.patient.repository.TurnRepository;
import com.clinicturn.api.patient.service.PatientService;
import com.clinicturn.api.patient.service.TurnService;
import com.clinicturn.api.patient.service.TurnStatusService;
import com.clinicturn.api.patient.util.TurnNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TurnServiceImpl implements TurnService {

    private final TurnRepository turnRepository;
    private final SpecialityService specialityService;
    private final PatientService patientService;
    private final TurnStatusService turnStatusService;
    private final DoctorService doctorService;
    private final RoomDoctorService roomDoctorService;

    @Override
    @Transactional
    public TurnResponse create(CreateTurnRequest request, Authentication authentication) {
        Speciality speciality = specialityService.getByIdAndReturnEntity(request.getSpecialityId());
        Patient patient = patientService.getByIdAndReturnEntity(request.getPatientId());
        List<String> roles = getRolesFromAuthentication(authentication);
        validateNotExistsValidTurn(patient, roles);
        TurnStatus turnStatus = turnStatusService.getByNameAndReturnEntity("WAITING");
        String nextTurnNumber = generateNextTurnNumber();
        Turn entity = mapFromCreateRequestToEntity(request, nextTurnNumber, speciality, patient, turnStatus);
        Turn saved = turnRepository.save(entity);
        return mapToResponse(saved, null);
    }

    @Override
    @Transactional
    public TurnResponse updateStatus(Long id, UpdateTurnStatusRequest request) {
        validateMatchingIds(id, request.getId());
        Turn turn = validateAndReturnEntityById(id);
        TurnStatus newStatus = turnStatusService.getByNameAndReturnEntity(request.getStatusName());
        StatusType statusType = StatusType.fromName(newStatus.getName());
        switch (statusType) {
            case CALLED -> {
                turn.setStatus(newStatus);
                turn.setCalledAt(turn.getLocalChronoTime());
            }
            case SEEN -> {
                turn.setStatus(newStatus);
                turn.setSeenAt(turn.getLocalChronoTime());
            }
            case COMPLETED -> {
                turn.setStatus(newStatus);
                turn.setCompletedAt(turn.getLocalChronoTime());
            }
            case CANCELLED -> {
                turn.setStatus(newStatus);
                turn.setCancelledAt(turn.getLocalChronoTime());
            }
        }
        turnRepository.save(turn);
        String doctorRoomNumber = null;
        if (turn.getDoctor() != null) {
            doctorRoomNumber = roomDoctorService.getRoomNumberFromDoctorByDoctorId(turn.getDoctor().getId());
        }
        return mapToResponse(turn, doctorRoomNumber);
    }

    @Override
    @Transactional
    public TurnResponse updateDoctor(Long id, UpdateTurnDoctorRequest request) {
        validateMatchingIds(id, request.getId());
        Turn entity = validateAndReturnEntityById(id);
        Doctor doctor = doctorService.getByIdAndReturnEntity(request.getDoctorId());
        entity.setDoctor(doctor);
        turnRepository.save(entity);
        String doctorRoomNumber = roomDoctorService.getRoomNumberFromDoctorByDoctorId(doctor.getId());
        return mapToResponse(entity, doctorRoomNumber);
    }

    @Override
    public List<TurnResponse> getAllActive() {
        return turnRepository.findAllActiveTurns().stream()
                .map(turn -> {
                    String doctorRoomNumber = null;
                    if (turn.getDoctor() != null) {
                        doctorRoomNumber = roomDoctorService.getRoomNumberFromDoctorByDoctorId(turn.getDoctor().getId());
                    }
                    return mapToResponse(turn, doctorRoomNumber);
                })
                .toList();
    }

    @Override
    public List<DoctorResponse> getAvailableDoctorsByTurnId(Long id) {
        Turn entity = validateAndReturnEntityById(id);
        return doctorService.getByIsActiveTrueAndSpecialityCode(entity.getSpeciality().getCode());
    }

    @Override
    public PacientCountResponse getPacientCount() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay().truncatedTo(ChronoUnit.MICROS);
        LocalDateTime end = today.plusDays(1).atStartOfDay().truncatedTo(ChronoUnit.MICROS);
        return new PacientCountResponse(turnRepository.countByCreatedAtBetween(start, end));
    }

    @Override
    public TurnResponse getLastCalledTurn() {
        Optional<Turn> lastTurnOpt = turnRepository.findTopByCalledAtIsNotNullOrderByCalledAtDesc();
        return lastTurnOpt.map(turn -> mapToResponse(turn, null)).orElse(null);
    }

    @Override
    public TurnCountResponse countWaitingTurns() {
        return new TurnCountResponse(turnRepository.countByStatus_Name("WAITING"));
    }

    @Override
    public TurnCountResponse countSeenTurns() {
        return new TurnCountResponse(turnRepository.countByStatus_Name("SEEN"));
    }

    private List<String> getRolesFromAuthentication(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))
                .toList();
    }

    private Turn validateAndReturnEntityById(Long id){
        return turnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turn not found with id: " + id));
    }

    private void validateNotExistsValidTurn(Patient patient, List<String> roles) {
        if (hasRole(roles, ClinicRoleType.RECEPTION.name())) {
            return;
        }
        Optional<Turn> turnOpt = turnRepository.findTopByPatient_IdOrderByCreatedAtDesc(patient.getId());
        if (turnOpt.isEmpty()) {
            return;
        }
        Turn lastTurn = turnOpt.get();
        if (!isTurnFinished(lastTurn)) {
            throw new TurnStillActiveException(
                    "Patient already has an active turn. Turn needs to be either completed or cancelled");
        }
    }

    private void validateMatchingIds(Long pathId, Long requestId) {
        if (!pathId.equals(requestId)) {
            throw new IdsMismatchException("Path id and request body id doesn't match");
        }
    }

    private boolean hasRole(List<String> roles, String role) {
        return roles.contains(role);
    }

    private boolean isTurnFinished(Turn turn) {
        String status = turn.getStatus().getName();
        return StatusType.COMPLETED.name().equals(status)
                || StatusType.CANCELLED.name().equals(status)
                || turn.getCompletedAt() != null
                || turn.getCancelledAt() != null;
    }

    private String generateNextTurnNumber(){
        Optional<String> lastNumberOpt = turnRepository.findLastTurnNumber();
        String lastNumber = lastNumberOpt.orElse(null);
        return TurnNumberGenerator.getNextTurnNumber(lastNumber);
    }

    private Turn mapFromCreateRequestToEntity(CreateTurnRequest request, String nextTurnNumber,
                                              Speciality speciality, Patient patient,
                                              TurnStatus turnStatus) {
        return Turn.builder()
                .number(nextTurnNumber)
                .speciality(speciality)
                .patient(patient)
                .reason(request.getReason())
                .status(turnStatus)
                .build();
    }

    private TurnResponse mapToResponse(Turn entity, String doctorRoomNumber) {
        return TurnResponse.builder()
                .id(entity.getId())
                .number(entity.getNumber())
                .speciality(TurnResponse.TurnSpecialityResponse.builder()
                        .id(entity.getSpeciality().getId())
                        .code(entity.getSpeciality().getCode())
                        .build())
                .patient(TurnResponse.TurnPatientResponse.builder()
                        .id(entity.getPatient().getId())
                        .fullName(entity.getPatient().getFullName())
                        .build())
                .doctor(entity.getDoctor() == null ? null :
                        TurnResponse.TurnDoctorResponse.builder()
                                .id(entity.getDoctor().getId())
                                .fullName(entity.getDoctor().getFullName())
                                .roomNumber(doctorRoomNumber)
                                .build())
                .reason(entity.getReason())
                .status(entity.getStatus().getName())
                .calledAt(entity.getCalledAt() == null ? null :
                        entity.getCalledAt().atZone(ZoneOffset.UTC).toInstant())
                .seenAt(entity.getSeenAt() == null ? null :
                        entity.getSeenAt().atZone(ZoneOffset.UTC).toInstant())
                .completedAt(entity.getCompletedAt() == null ? null :
                        entity.getCompletedAt().atZone(ZoneOffset.UTC).toInstant())
                .cancelledAt(entity.getCancelledAt() == null ? null :
                        entity.getCancelledAt().atZone(ZoneOffset.UTC).toInstant())
                .createdAt(entity.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .updatedAt(entity.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant())
                .build();
    }
}
