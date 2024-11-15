package com.energytracker.userservice.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {

    private String email;
    private String fullName;
    private String password;
    private String role;
    private Boolean isActive;
    private String profilePicturePath;

}
