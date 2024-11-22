package com.energytracker.userservice.application.port.outbound;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepositoryPort {

    boolean existsByEmail(String email);

    UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);
}
