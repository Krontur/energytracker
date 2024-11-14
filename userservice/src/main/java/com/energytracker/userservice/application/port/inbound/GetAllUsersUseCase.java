package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.application.dto.UserResponseDto;
import java.util.List;

public interface GetAllUsersUseCase {

    List<UserResponseDto> getAllUsers();
}
