package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.inbound.GetAllUsersUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.energytracker.userservice.application.mapper.UserMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, GetAllUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        User user = UserMapper.createUserRequestDtoToDomain(createUserRequestDto);

        if(userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepositoryPort.createUser(UserMapper.createUserRequestDomainToDto(user));
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepositoryPort.getAllUsers();
    }
}
