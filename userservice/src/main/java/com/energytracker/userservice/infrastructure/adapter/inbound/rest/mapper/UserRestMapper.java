package com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserResponseRestDto;

public class UserRestMapper {

    public static CreateUserRequestDto createUserRequestFromRestDtoToDto(CreateUserRequestRestDto createUserRequestRestDto) {
        return new CreateUserRequestDto(
                createUserRequestRestDto.getEmail(),
                createUserRequestRestDto.getFullName(),
                createUserRequestRestDto.getPassword(),
                createUserRequestRestDto.getRole(),
                createUserRequestRestDto.getIsActive(),
                createUserRequestRestDto.getProfilePicturePath()
        );
    }

    public static CreateUserRequestRestDto createUserRequestFromDtoToRestDto(CreateUserRequestDto createUserRequestDto) {
        return CreateUserRequestRestDto.builder()
                .email(createUserRequestDto.getEmail())
                .fullName(createUserRequestDto.getFullName())
                .password(createUserRequestDto.getPassword())
                .role(createUserRequestDto.getRole())
                .isActive(createUserRequestDto.getIsActive())
                .profilePicturePath(createUserRequestDto.getProfilePicturePath())
                .build();
    }

    public static UserResponseRestDto userResponseFromDtoToRestDto(UserResponseDto userResponseDto) {
        return new UserResponseRestDto(
                userResponseDto.getUserAccountId(),
                userResponseDto.getEmail(),
                userResponseDto.getFullName(),
                userResponseDto.getPassword(),
                userResponseDto.getRole(),
                userResponseDto.getIsActive(),
                userResponseDto.getCreatedDate(),
                userResponseDto.getUpdatedDate(),
                userResponseDto.getProfilePicturePath()
        );
    }
}
