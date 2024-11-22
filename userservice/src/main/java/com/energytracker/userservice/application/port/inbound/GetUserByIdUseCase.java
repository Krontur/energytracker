package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.application.dto.UserResponseDto;

public interface GetUserByIdUseCase {

    UserResponseDto getUserById(Long userId);

}
