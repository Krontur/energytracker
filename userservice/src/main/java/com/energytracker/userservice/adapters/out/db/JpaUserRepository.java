package com.energytracker.userservice.adapters.out.db;

import com.energytracker.userservice.domain.model.User;
import com.energytracker.userservice.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
