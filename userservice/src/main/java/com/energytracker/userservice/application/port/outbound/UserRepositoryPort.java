package com.energytracker.userservice.application.port.outbound;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface UserRepositoryPort {

    boolean existsByEmail(String email);

    UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);
}