package com.energytracker.userservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    private Long id;
    private String token;
    private TokenType type = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    private User user;

}
