package com.energytracker.userservice.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAccountId;

    @NotNull (message = "Email cannot be null")
    @Email (message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotNull (message = "Password cannot be null")
    private String password;

    @NotNull (message = "Full name cannot be null")
    private String FullName;

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
