package com.energytracker.userservice.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    private String email;
    private String fullName;
    private String password;
    private String role;
    private Boolean isActive;
    private String profilePicturePath;

}
