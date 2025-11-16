package com.clinicturn.api.security.provider;

import com.clinicturn.api.security.adapter.CustomUserDetails;
import com.clinicturn.api.security.dto.response.AccessTokenResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenValiditySec}")
    private long jwtExpirationSec;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AccessTokenResult generateAccessToken(CustomUserDetails user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(jwtExpirationSec);

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))
                .toList();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("type", "ACCESS")
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return new AccessTokenResult(token, exp);
    }

    public boolean isValid(Claims claims) {
        try {
            return claims.getExpiration()
                    .after(new Date());
        } catch (JwtException ex) {
            log.debug("JWT validation failed: {}", ex.getMessage());
            return false;
        }
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
