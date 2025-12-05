package com.clinicturn.api.security.config;

import com.clinicturn.api.security.filter.JwtAuthenticationFilter;
import com.clinicturn.api.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/logout").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/api/v1/auth/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/clinic").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/clinic/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/clinic").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/schedule").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/clinic/{clinicId}/schedules").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/room").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/room").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/clinic/room/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/room/{id}").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/clinic/{clinicId}/rooms/available").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/speciality").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/speciality/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/speciality").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/doctor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/doctor").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/clinic/doctor/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/doctor/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinic/doctor/active").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/clinic/doctor/assign-room").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/patient/me").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn").hasRole("QUEUE_MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/patient/turn").hasAnyRole("PATIENT", "RECEPTION")
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/count").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/count/waiting").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/count/seen").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/last-called").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/patient/turn/{id}/status").hasAnyRole("PATIENT", "QUEUE_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/{id}/doctor").hasRole("QUEUE_MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/patient/turn/{id}/doctor").hasRole("QUEUE_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/status").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/status/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/turn/status/by-name/{name}").authenticated()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider);
    }
}
