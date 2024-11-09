package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateUserRequestPersistenceDto {

    private String email;
    private String fullName;
    private String password;
    private String role;
    private Boolean isActive;
    private String profilePicturePath;

}
