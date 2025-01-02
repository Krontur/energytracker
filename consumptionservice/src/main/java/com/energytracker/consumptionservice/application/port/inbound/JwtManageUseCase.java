package com.energytracker.consumptionservice.application.port.inbound;

public interface JwtManageUseCase {

    boolean isExpired(String token);

    boolean validateToken(String token);

    String extractEmail(String token);

    String extractFullName(String token);

    String extractUsername(String token);

    String extractRole(String token);

    boolean isEnabled(String token);
}
