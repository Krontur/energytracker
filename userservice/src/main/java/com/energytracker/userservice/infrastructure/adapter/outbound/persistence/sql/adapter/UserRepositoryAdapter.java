package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserPersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper.UserPersistenceMapper;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaUserRepositoryPort;
import org.springframework.stereotype.Component;

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
    public UserDto createUser(UserDto userDto) {
        UserPersistenceDto userPersistenceDto = UserPersistenceMapper.fromDtoToPersistenceDto(userDto);
        UserEntity createdUser = jpaUserRepositoryPort.save(UserPersistenceMapper.fromPersistenceDtoToEntity(userPersistenceDto));

        return UserPersistenceMapper.fromPersistenceDtoToDto(UserPersistenceMapper.fromEntityToPersistenceDto(createdUser));
    }

}
