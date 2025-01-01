package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.domain.model.User;

public interface JwtManageUseCase {

    String generateToken(User user);

    String generateRefreshToken(User user);

    void saveToken(String token, User user);

    String getEmailFromToken(String token);

    boolean isValidToken(String token, User user);

}
