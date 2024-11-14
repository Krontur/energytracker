package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserResponsePersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper.UserPersistenceMapper;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaUserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepositoryPort jpaUserRepositoryPort;

    public UserRepositoryAdapter(JpaUserRepositoryPort jpaUserRepositoryPort) {
        this.jpaUserRepositoryPort = jpaUserRepositoryPort;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepositoryPort.existsByEmail(email);
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        UserEntity createdUser = jpaUserRepositoryPort.save(UserPersistenceMapper.createUserRequestFromDtoToEntity(createUserRequestDto));

        return UserPersistenceMapper.userResponseFromPersistenceDtoToDto(UserPersistenceMapper.userResponseFromEntityToPersistenceDto(createdUser));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = jpaUserRepositoryPort.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<UserResponseDto>();
        users.forEach(user -> userResponseDtos
                .add(UserPersistenceMapper.userResponseFromPersistenceDtoToDto
                        (UserPersistenceMapper.userResponseFromEntityToPersistenceDto(user))));
        return userResponseDtos;
    }

}
