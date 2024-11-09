package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.CreateUserRequestPersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserResponsePersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;

public class UserPersistenceMapper {

    public static UserEntity createUserRequestFromDtoToEntity(CreateUserRequestDto createUserRequestDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(createUserRequestDto.getEmail());
        userEntity.setFullName(createUserRequestDto.getFullName());
        userEntity.setPassword(createUserRequestDto.getPassword());
        userEntity.setRole(createUserRequestDto.getRole());
        userEntity.setIsActive(createUserRequestDto.getIsActive());
        userEntity.setProfilePicturePath(createUserRequestDto.getProfilePicturePath());

        return userEntity;

    }

    public static CreateUserRequestPersistenceDto createUserRequestFromDtoToPersistenceDto(CreateUserRequestDto createUserRequestDto) {
        return new CreateUserRequestPersistenceDto(
                createUserRequestDto.getEmail(),
                createUserRequestDto.getFullName(),
                createUserRequestDto.getPassword(),
                createUserRequestDto.getRole(),
                createUserRequestDto.getIsActive(),
                createUserRequestDto.getProfilePicturePath()
        );
    }

    public static UserResponsePersistenceDto userResponseFromEntityToPersistenceDto(UserEntity userEntity) {
        return new UserResponsePersistenceDto(
                userEntity.getUserAccountId(),
                userEntity.getEmail(),
                userEntity.getFullName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getIsActive(),
                userEntity.getCreatedDate(),
                userEntity.getUpdatedDate(),
                userEntity.getProfilePicturePath()
        );
    }

    public static UserResponseDto userResponseFromPersistenceDtoToDto(UserResponsePersistenceDto userResponsePersistenceDto) {
        return new UserResponseDto(
                userResponsePersistenceDto.getUserAccountId(),
                userResponsePersistenceDto.getEmail(),
                userResponsePersistenceDto.getFullName(),
                userResponsePersistenceDto.getPassword(),
                userResponsePersistenceDto.getRole(),
                userResponsePersistenceDto.getIsActive(),
                userResponsePersistenceDto.getCreatedDate(),
                userResponsePersistenceDto.getUpdatedDate(),
                userResponsePersistenceDto.getProfilePicturePath()
        );
    }
}
