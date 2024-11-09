package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserRestDto;

public interface CreateUserUseCase {

    UserDto createUser(UserDto userDto);
}
