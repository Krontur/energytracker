package com.energytracker.userservice.application.port.outbound;

import com.energytracker.userservice.domain.model.LoginRequest;
import com.energytracker.userservice.domain.model.Token;

import java.util.List;

public interface TokenRepositoryPort {

    Token save(Token token);

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long userAccountId);

    void saveAll(List<Token> validUserTokens);

    Token findByToken(String token);
}
