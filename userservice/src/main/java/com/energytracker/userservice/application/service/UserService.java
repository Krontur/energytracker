package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.inbound.GetAllUsersUseCase;
import com.energytracker.userservice.application.port.inbound.GetUserByEmailUseCase;
import com.energytracker.userservice.application.port.inbound.GetUserByIdUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.energytracker.userservice.application.mapper.UserMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, GetAllUsersUseCase, GetUserByIdUseCase, GetUserByEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        User user = UserMapper.createUserRequestDtoToDomain(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepositoryPort.createUser(UserMapper.createUserRequestDomainToDto(user));
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepositoryPort.getAllUsers();
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        return userRepositoryPort.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        UserResponseDto userResponseDto = userRepositoryPort.getUserByEmail(email);
        return UserMapper.userResponseDtoToDomain(userResponseDto);
    }
}
