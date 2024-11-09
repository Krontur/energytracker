package com.energytracker.userservice.application.mapper;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.domain.model.Role;
import com.energytracker.userservice.domain.model.User;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserRestDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserPersistenceDto;

public class UserMapper {

    public static UserDto fromDomainToDto(User user) {
        return new UserDto(user.getUserAccountId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getRole().toString(),
                user.getIsActive(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getProfilePicturePath()
        );
    }

    public static User fromDtoToDomain(UserDto userDto) {
        return new User(userDto.getUserAccountId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFullName(),
                Role.valueOf(userDto.getRole()),
                userDto.getIsActive(),
                userDto.getProfilePicturePath()
        );
    }
}
