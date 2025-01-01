package com.energytracker.devicecatalog.application.port.inbound;

import java.util.List;

public interface JwtManageUseCase {

    boolean isExpired(String token);

    boolean validateToken(String token);

    String extractEmail(String token);

    String extractFullName(String token);

    String extractUsername(String token);

    String extractRole(String token);

    boolean isEnabled(String token);
}
