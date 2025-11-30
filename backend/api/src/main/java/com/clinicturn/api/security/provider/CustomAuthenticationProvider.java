package com.clinicturn.api.security.provider;

import com.clinicturn.api.security.adapter.CustomUserDetails;
import com.clinicturn.api.security.exception.ClinicUserDisabledException;
import com.clinicturn.api.security.exception.ClinicUserLockedException;
import com.clinicturn.api.security.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String requestPassword = (String) authentication.getCredentials();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        validateUserWithSecurityFlags(userDetails, requestPassword);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        log.debug("User {} authenticated successfully", username);
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void validateUserWithSecurityFlags(CustomUserDetails userDetails, String requestPassword) {
        assertIsEnabled(userDetails.isEnabled());
        assertIsAccountNonLocked(userDetails.isAccountNonLocked());
        assertPasswordMatches(userDetails.getPassword(), requestPassword);
    }

    private void assertIsEnabled(boolean isEnabled) {
        if (!isEnabled) {
            throw new ClinicUserDisabledException("User account is disabled");
        }
    }

    private void assertIsAccountNonLocked(boolean isAccountNonLocked) {
        if (!isAccountNonLocked) {
            throw new ClinicUserLockedException("User account is locked");
        }
    }

    private void assertPasswordMatches(String userPassword, String requestPassword) {
        if (!passwordEncoder.matches(requestPassword, userPassword)) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}