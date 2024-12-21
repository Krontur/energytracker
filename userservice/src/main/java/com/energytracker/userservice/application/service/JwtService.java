package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.port.inbound.JwtManageUseCase;
import com.energytracker.userservice.application.port.outbound.TokenRepositoryPort;
import com.energytracker.userservice.domain.model.Token;
import com.energytracker.userservice.domain.model.TokenType;
import com.energytracker.userservice.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtManageUseCase {

    private final TokenRepositoryPort tokenRepositoryPort;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration-time}")
    private long tokenExpirationTime;

    @Value("${application.security.jwt.refresh-token.expiration-time}")
    private long refreshExpirationTime;

    @Override
    public String generateToken(User user) {
        return buildToken(user, tokenExpirationTime);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, refreshExpirationTime);
    }

    @Override
    public void saveToken(String token, User user) {

        Token toSaveToken = new Token(
                null,
                token,
                TokenType.BEARER,
                false,
                false,
                user
        );

        tokenRepositoryPort.save(toSaveToken);
    }

    private String buildToken(User user, Long experationTime) {
        return Jwts.builder()
                .id(user.getUserAccountId().toString())
                .claims(Map.of("role", user.getRole().name(), "fullName", user.getFullName()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + experationTime))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String getEmailFromToken(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public boolean isValidToken(String token, User user) {

        String email = getEmailFromToken(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }
}
