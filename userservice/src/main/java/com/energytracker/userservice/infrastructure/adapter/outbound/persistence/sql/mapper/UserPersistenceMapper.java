package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.domain.model.Role;
import com.energytracker.userservice.domain.model.Token;
import com.energytracker.userservice.domain.model.User;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.CreateUserRequestPersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserResponsePersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.TokenEntity;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static UserEntity userDomainToEntity(User user) {
        List<TokenEntity> tokenEntityList = new ArrayList<>();
        if (user.getTokens() != null) {
            user.getTokens().forEach(token -> tokenEntityList.add(
                    TokenPersistenceMapper.tokenDomainToEntity(token)));
        }
        UserEntity userEntity = new UserEntity(
                user.getUserAccountId(),
                user.getEmail(),
                user.getFullName(),
                user.getPassword(),
                user.getRole().name(),
                user.getIsActive(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getProfilePicturePath(),
                tokenEntityList
        );
        return userEntity;
    }

    public static User userEntityToDomain(UserEntity userEntity) {
        List<Token> tokenList = new ArrayList<>();
        userEntity.getTokens().forEach(token -> tokenList.add(
                TokenPersistenceMapper.tokenEntityToDomain(token)));

        return new User(
                userEntity.getUserAccountId(),
                userEntity.getEmail(),
                userEntity.getFullName(),
                userEntity.getPassword(),
                Role.valueOf(userEntity.getRole()),
                userEntity.getIsActive(),
                userEntity.getCreatedDate(),
                userEntity.getUpdatedDate(),
                userEntity.getProfilePicturePath(),
                tokenList
        );
    }
}
