package com.energytracker.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(Long userAccountId,
                String email,
                String password,
                String fullName,
                Role role,
                Boolean isActive,
                String profilePicturePath) {
        this.userAccountId = userAccountId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
        this.profilePicturePath = profilePicturePath;
    }

    private Long userAccountId;
    private String email;
    private String password;
    private String fullName;
    private Role role;
    private Boolean isActive;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String profilePicturePath;
    private List<Token> tokens;


}
