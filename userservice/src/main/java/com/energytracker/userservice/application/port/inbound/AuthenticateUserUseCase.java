package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.application.dto.LoginRequestDto;
import com.energytracker.userservice.application.dto.RegisterRequestDto;
import com.energytracker.userservice.application.dto.TokenResponseDto;
import com.energytracker.userservice.domain.model.User;

public interface AuthenticateUserUseCase {

    TokenResponseDto login(LoginRequestDto loginRequestDto);

    void logout(String token);

    TokenResponseDto refreshToken(String authHeader);

    TokenResponseDto register(RegisterRequestDto registerRequestDto);

}
