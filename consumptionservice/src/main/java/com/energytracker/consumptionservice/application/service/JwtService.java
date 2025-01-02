package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.inbound.JwtManageUseCase;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class JwtService implements JwtManageUseCase {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Override
    public boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature or malformed token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    @Override
    public String extractEmail(String token) {
        return extractClaims(token).get("sub", String.class);
    }

    @Override
    public String extractFullName(String token) {
        return extractClaims(token).get("fullName", String.class);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    @Override
    public boolean isEnabled(String token) {
        Claims claims = extractClaims(token);
        return claims.get("isEnabled", Boolean.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        try {
            byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(secretKeyBytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid Base64-encoded secret key", e);
        }
    }

}
