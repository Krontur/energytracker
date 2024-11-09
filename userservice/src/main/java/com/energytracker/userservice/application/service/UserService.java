package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.application.mapper.UserMapper;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserRestDto;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public UserDto createUser(UserDto userDto) {

        if(userRepositoryPort.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepositoryPort.createUser(userDto);
    }
}
