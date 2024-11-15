package com.energytracker.userservice.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

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

    @NotNull (message = "Email cannot be null")
    @Email (message = "Email should be valid")
    private String email;

    @NotNull (message = "Password cannot be null")
    private String password;

    @NotNull (message = "Full name cannot be null")
    private String fullName;

    @NotNull (message = "Role cannot be null")
    private Role role;

    @NotNull (message = "Is active cannot be null")
    private Boolean isActive;

    @NotNull (message = "Created date cannot be null")
    @CreatedDate
    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String profilePicturePath;


}
