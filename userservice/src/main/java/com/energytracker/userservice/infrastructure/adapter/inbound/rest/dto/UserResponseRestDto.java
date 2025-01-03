package com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseRestDto {

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
