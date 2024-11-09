package com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateUserRequestRestDto {

    private Long userAccountId;
    private String email;
    private String fullName;
    private String password;
    private String role;
    private Boolean isActive;
    private String profilePicturePath;

}
