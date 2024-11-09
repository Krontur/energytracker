package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserPersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;

public class UserPersistenceMapper {

    public static UserPersistenceDto fromEntityToPersistenceDto(UserEntity userEntity) {
        return new UserPersistenceDto(userEntity.getUserAccountId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getFullName(),
                userEntity.getRole(),
                userEntity.getIsActive(),
                userEntity.getCreatedDate(),
                userEntity.getUpdatedDate(),
                userEntity.getProfilePicturePath()
        );
    }

    public static UserEntity fromPersistenceDtoToEntity(UserPersistenceDto userPersistenceDto) {

        return new UserEntity(userPersistenceDto.getUserAccountId(),
                userPersistenceDto.getEmail(),
                userPersistenceDto.getPassword(),
                userPersistenceDto.getFullName(),
                userPersistenceDto.getRole(),
                userPersistenceDto.getIsActive(),
                userPersistenceDto.getCreatedDate(),
                userPersistenceDto.getUpdatedDate(),
                userPersistenceDto.getProfilePicturePath());

    }

    public static UserPersistenceDto fromDtoToPersistenceDto(UserDto userDto) {
        return new UserPersistenceDto(
                userDto.getUserAccountId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFullName(),
                userDto.getRole(),
                userDto.getIsActive(),
                userDto.getCreatedDate(),
                userDto.getUpdatedDate(),
                userDto.getProfilePicturePath()
        );
    }

    public static UserDto fromPersistenceDtoToDto(UserPersistenceDto userPersistenceDto) {
        return new UserDto(
                userPersistenceDto.getUserAccountId(),
                userPersistenceDto.getEmail(),
                userPersistenceDto.getPassword(),
                userPersistenceDto.getFullName(),
                userPersistenceDto.getRole(),
                userPersistenceDto.getIsActive(),
                userPersistenceDto.getCreatedDate(),
                userPersistenceDto.getUpdatedDate(),
                userPersistenceDto.getProfilePicturePath()
        );
    }


}
