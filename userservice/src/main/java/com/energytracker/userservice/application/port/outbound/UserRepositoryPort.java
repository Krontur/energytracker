package com.energytracker.userservice.application.port.outbound;

import com.energytracker.userservice.application.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserRepositoryPort {

    boolean existsByEmail(String email);

    UserDto createUser(UserDto userDto);
}
