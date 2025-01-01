package com.energytracker.userservice.infrastructure.config;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@DependsOn("userService")
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${demo.init}")
    private boolean demoInit;

    private final CreateUserUseCase createUserUseCase;

    public DatabaseInitializer(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        log.info("DatabaseInitializer created");
    }

    private void initializeDatabase() {
        for (int i = 0; i < 10; i++) {
            log.info("Creating user {}", i);
            String role;
            boolean isActive;
            if(i % 2 == 0){ role = "ADMIN"; } else { role = "USER"; }
            if(i % 2 == 0){ isActive = true; } else { isActive = false; }
            createUserUseCase.createUser(new CreateUserRequestDto(
                    "user" + i + "@example.com",
                    "User" + i,
                    "password" + i*23,
                    role,
                    isActive,
                    "path/to/avatar" + i
                    ));
        }

    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (demoInit) {
                log.info("Initializing database users");
                initializeDatabase();
            }
        } catch (Exception e) {
            log.error("Error initializing database users", e);
            throw new RuntimeException("Error initializing database users", e);
        }
    }
}
