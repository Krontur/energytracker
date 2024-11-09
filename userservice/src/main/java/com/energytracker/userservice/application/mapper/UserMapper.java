package com.energytracker.userservice.application.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.domain.model.User;

public class UserMapper {

    public static CreateUserRequestDto createUserRequestFromDomainToDto(User user) {
        return new CreateUserRequestDto(
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getRole().toString(),
                user.getIsActive(),
                user.getProfilePicturePath()
        );
    }

}
