package com.energytracker.userservice.domain.repository;

import com.energytracker.userservice.domain.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    boolean existsByEmail(String email);

    User save(User user);
}
