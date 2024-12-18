package com.energytracker.userservice.application.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.domain.model.Role;
import com.energytracker.userservice.domain.model.User;

public class UserMapper {

    public static User createUserRequestDtoToDomain(CreateUserRequestDto createUserRequestDto) {

        User user = new User();

        user.setEmail(createUserRequestDto.getEmail());
        user.setFullName(createUserRequestDto.getFullName());
        user.setPassword(createUserRequestDto.getPassword());
        user.setRole(Role.valueOf(createUserRequestDto.getRole()));
        user.setIsActive(createUserRequestDto.getIsActive());
        user.setProfilePicturePath(createUserRequestDto.getProfilePicturePath());
        return user;
    };

    public static CreateUserRequestDto createUserRequestDomainToDto(User user) {

            CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();

            createUserRequestDto.setEmail(user.getEmail());
            createUserRequestDto.setFullName(user.getFullName());
            createUserRequestDto.setPassword(user.getPassword());
            createUserRequestDto.setRole(user.getRole().name());
            createUserRequestDto.setIsActive(user.getIsActive());
            createUserRequestDto.setProfilePicturePath(user.getProfilePicturePath());
            return createUserRequestDto;
    }

    public static User userResponseDtoToDomain(UserResponseDto userResponseDto) {

            User user = new User();

            user.setUserAccountId(userResponseDto.getUserAccountId());
            user.setEmail(userResponseDto.getEmail());
            user.setFullName(userResponseDto.getFullName());
            user.setPassword(userResponseDto.getPassword());
            user.setRole(Role.valueOf(userResponseDto.getRole()));
            user.setIsActive(userResponseDto.getIsActive());
            user.setProfilePicturePath(userResponseDto.getProfilePicturePath());
            return user;
    }
}
