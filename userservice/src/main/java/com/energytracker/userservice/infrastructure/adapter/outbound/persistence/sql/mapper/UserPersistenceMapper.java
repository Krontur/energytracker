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

    public static UserResponseDto userResponseEntityToDto(UserEntity user) {
        return new UserResponseDto(
                user.getUserAccountId(),
                user.getEmail(),
                user.getFullName(),
                user.getPassword(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getProfilePicturePath()
        );
    }
}
