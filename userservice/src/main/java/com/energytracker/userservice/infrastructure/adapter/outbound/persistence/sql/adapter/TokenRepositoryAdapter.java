package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.userservice.application.port.outbound.TokenRepositoryPort;
import com.energytracker.userservice.domain.model.LoginRequest;
import com.energytracker.userservice.domain.model.Token;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.TokenEntity;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper.TokenPersistenceMapper;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenRepositoryAdapter implements TokenRepositoryPort {

    private final JpaTokenRepositoryPort jpaTokenRepositoryPort;

    @Override
    public Token login(LoginRequest loginRequest) {
        return null;
    }


    @Override
    public void logout(String token) {

    }

    @Override
    public Token save(Token token) {
        TokenEntity tokenEntity = TokenPersistenceMapper.tokenDomainToEntity(token);
        TokenEntity savedTokenEntity = jpaTokenRepositoryPort.save(tokenEntity);

        return TokenPersistenceMapper.tokenEntityToDomain(savedTokenEntity);
    }

    @Override
    public List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long userAccountId) {
        return List.of();
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        List<TokenEntity> validUserTokenEntities = new ArrayList<>();
        validUserTokens.forEach(token ->
            validUserTokenEntities.add(TokenPersistenceMapper.tokenDomainToEntity(token))
        );
        jpaTokenRepositoryPort.saveAll(validUserTokenEntities);

    }


}
