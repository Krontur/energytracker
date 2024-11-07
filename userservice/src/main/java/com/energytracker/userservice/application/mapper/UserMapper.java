package com.energytracker.userservice.application.mapper;

import com.energytracker.userservice.application.dto.UserDTO;
import com.energytracker.userservice.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());
        user.setIsActive(userDTO.getIsActive());
        user.setProfilePicturePath(userDTO.getProfilePicturePath());
        return user;

    }

    public UserDTO toDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserAccountId(user.getUserAccountId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setRole(user.getRole());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setProfilePicturePath(user.getProfilePicturePath());
        return userDTO;

    }

}
