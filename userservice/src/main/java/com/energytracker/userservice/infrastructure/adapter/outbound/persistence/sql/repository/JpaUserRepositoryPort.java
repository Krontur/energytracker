package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserRepositoryPort extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

}
