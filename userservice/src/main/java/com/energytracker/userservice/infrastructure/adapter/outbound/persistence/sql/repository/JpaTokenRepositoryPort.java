package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTokenRepositoryPort extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);
}
