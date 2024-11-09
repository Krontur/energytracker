package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;

public interface CreateUserUseCase {

    UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);
}
