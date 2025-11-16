package com.clinicturn.api.auth.service.impl;

import com.clinicturn.api.auth.dto.response.RefreshTokenResult;
import com.clinicturn.api.auth.model.ClinicUser;
import com.clinicturn.api.auth.model.RefreshToken;
import com.clinicturn.api.auth.repository.RefreshTokenRepository;
import com.clinicturn.api.auth.service.ClinicUserService;
import com.clinicturn.api.auth.service.RefreshTokenService;
import com.clinicturn.api.auth.utils.HashUtils;
import com.clinicturn.api.security.adapter.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final ClinicUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.refresh-token.length}")
    private int tokenLength;

    @Value("${security.refresh-token.expiration-days}")
    private int expirationDays;

    @Override
    @Transactional
    public RefreshTokenResult create(CustomUserDetails userDetails) {
        String rawToken = generateRawToken();

        ClinicUser userEntity = userService.findByIdAndReturnEntity(userDetails.getId());

        RefreshToken refreshToken = buildRefreshToken(userEntity, rawToken);

        RefreshToken saved = repository.save(refreshToken);

        return new RefreshTokenResult(rawToken, saved);
    }

    @Override
    @Transactional
    public void revoke(String rawToken) {
        RefreshToken tokenEntity = findByFingerprint(HashUtils.sha256(rawToken));
        tokenEntity.setIsRevoked(true);
        repository.save(tokenEntity);
    }

    @Override
    public RefreshToken verifyAndGet(String rawToken) {
        RefreshToken tokenEntity = findByFingerprint(HashUtils.sha256(rawToken));

        validateRefreshToken(rawToken, tokenEntity);

        return tokenEntity;
    }

    private String generateRawToken() {
        byte[] randomBytes = new byte[tokenLength];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private RefreshToken buildRefreshToken(ClinicUser user, String rawToken) {
        return RefreshToken.builder()
                .user(user)
                .fingerprint(HashUtils.sha256(rawToken))
                .tokenHash(passwordEncoder.encode(rawToken))
                .expiresAt(LocalDateTime.now().plusDays(expirationDays).truncatedTo(ChronoUnit.MICROS))
                .build();
    }

    private RefreshToken findByFingerprint(String fingerprint){
        return repository.findByFingerprint(fingerprint)
                // Resource Not Found
                .orElseThrow(() -> new RuntimeException("Refresh Token not found with fingerprint provided"));
    }

    private void validateRefreshToken(String rawToken, RefreshToken entityToken) {
        assertTokenIsNotRevokedOrExpired(entityToken);
        assertTokenMatch(rawToken, entityToken);
    }

    private void assertTokenIsNotRevokedOrExpired(RefreshToken token) {
        if (token.getIsRevoked() || token.getExpiresAt().isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)))
            // RefreshTokenAlreadyExpired
            throw new RuntimeException("Refresh token expired or revoked");
    }

    private void assertTokenMatch(String rawToken, RefreshToken entityToken) {
        if (!passwordEncoder.matches(rawToken, entityToken.getTokenHash()))
            // RefreshTokenMismatch
            throw new RuntimeException("Invalid refresh token");
    }
}
