package com.energytracker.userservice.infrastructure.config;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@DependsOn("userService")
public class DatabaseInitializer implements CommandLineRunner {

    private final CreateUserUseCase createUserUseCase;

    public DatabaseInitializer(@Qualifier("userService") CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        log.info("DatabaseInitializer created");
    }

    private void initializeDatabase() {
        System.out.println("Initializing database users");
        log.info("Initializing database users");
        for (int i = 0; i < 10; i++) {
            log.info("Creating user {}", i);
            String role;
            boolean isActive;
            if(i % 2 == 0){ role = "ADMIN"; } else { role = "USER"; };
            if(i % 2 == 0){ isActive = true; } else { isActive = false; };
            createUserUseCase.createUser(new CreateUserRequestDto(
                    "user" + i + "@example.com",
                    "User" + i,
                    "password",
                    role,
                    isActive,
                    "path/to/avatar" + i
                    ));
        }

    }

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }
}
