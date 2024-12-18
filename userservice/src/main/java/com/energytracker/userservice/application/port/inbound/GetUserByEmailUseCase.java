package com.energytracker.userservice.application.port.inbound;

import com.energytracker.userservice.domain.model.User;

public interface GetUserByEmailUseCase {

    User getUserByEmail(String email);


}
