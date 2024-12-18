package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.port.inbound.GetUserByEmailUseCase;
import com.energytracker.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetUserByEmailUseCase getUserByEmailUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = getUserByEmailUseCase.getUserByEmail(username);
        if (user == null) new UsernameNotFoundException("User not found");
        return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .build();
        };
}

