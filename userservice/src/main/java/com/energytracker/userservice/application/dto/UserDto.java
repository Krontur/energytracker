package com.energytracker.userservice.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long userAccountId;
    private String email;
    private String fullName;
    private String password;
    private String role;
    private Boolean isActive;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String profilePicturePath;

}
