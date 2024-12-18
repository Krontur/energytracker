package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Table(name = "user_account")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

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
    private String fullName;

    @NotNull (message = "Role cannot be null")
    private String role;

    @NotNull (message = "Is active cannot be null")
    private Boolean isActive;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private LocalDate updatedDate;

    private String profilePicturePath;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<TokenEntity> tokens;

}
