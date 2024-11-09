package com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.domain.model.Role;
import com.energytracker.userservice.domain.model.User;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserRestDto;

public class UserRestMapper {

    public static UserDto fromRestDtoToDto(UserRestDto userRestDto) {
        return new UserDto(
                userRestDto.getUserAccountId(),
                userRestDto.getFullName(),
                userRestDto.getEmail(),
                userRestDto.getPassword(),
                userRestDto.getRole(),
                userRestDto.getIsActive(),
                userRestDto.getCreatedDate(),
                userRestDto.getUpdatedDate(),
                userRestDto.getProfilePicturePath()
        );
    }

    public static UserRestDto fromDtoToRestDto(UserDto userDto) {
        return new UserRestDto(
                userDto.getUserAccountId(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getIsActive(),
                userDto.getCreatedDate(),
                userDto.getUpdatedDate(),
                userDto.getProfilePicturePath()
        );
    }

}
