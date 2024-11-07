package com.energytracker.userservice.application.dto;

import com.energytracker.userservice.domain.model.Role;
import lombok.Data;

@Data
public class UserDTO {

    private Long userAccountId;
    private String email;
    private String FullName;
    private String password;
    private Role role;
    private Boolean isActive;
    private String profilePicturePath;

}
