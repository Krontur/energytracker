package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public Claims getClaimsFromToken(String token) {
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
