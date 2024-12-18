package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.domain.model.Token;
import com.energytracker.userservice.domain.model.TokenType;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.TokenEntity;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.TokenTypeEntity;

public class TokenPersistenceMapper {

    public static TokenEntity tokenDomainToEntity(Token token) {
        return new TokenEntity(
                token.getId(),
                token.getToken(),
                TokenTypeEntity.valueOf(token.getType().name()),
                token.isRevoked(),
                token.isExpired(),
                UserPersistenceMapper.userDomainToEntity(token.getUser()));
    }

    public static Token tokenEntityToDomain(TokenEntity tokenEntity) {
        return new Token(
                tokenEntity.getId(),
                tokenEntity.getToken(),
                TokenType.valueOf(tokenEntity.getType().name()),
                tokenEntity.isRevoked(),
                tokenEntity.isExpired(),
                UserPersistenceMapper.userEntityToDomain(tokenEntity.getUserEntity()));
    }
}

