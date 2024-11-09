package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        if(userRepositoryPort.existsByEmail(createUserRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepositoryPort.createUser(createUserRequestDto);
    }
}
