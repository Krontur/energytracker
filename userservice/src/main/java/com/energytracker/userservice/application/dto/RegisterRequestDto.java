package com.energytracker.userservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDto {

    private String email;
    private String fullName;
    private String password;

}
