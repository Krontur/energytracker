package com.energytracker.userservice.domain.service;

import com.energytracker.userservice.domain.model.User;
import com.energytracker.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Long createUser(User user) {
        return 0L;
    }
}
