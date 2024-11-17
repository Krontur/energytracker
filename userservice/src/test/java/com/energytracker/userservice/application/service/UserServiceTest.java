package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        assertNotNull(userRepositoryPort);
    }

    @Test
    public void testCreateUser() {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(
                "test@email.com",
                "Test User",
                "password",
                "USER",
                true,
                "profile-picture.jpg");

        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                createUserRequestDto.getEmail(),
                createUserRequestDto.getFullName(),
                createUserRequestDto.getPassword(),
                createUserRequestDto.getRole(),
                createUserRequestDto.getIsActive(),
                LocalDate.now(),
                LocalDate.now(),
                createUserRequestDto.getProfilePicturePath());

        when(userRepositoryPort.createUser(createUserRequestDto)).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(createUserRequestDto);

        assertEquals(createUserRequestDto.getEmail(), result.getEmail());
        assertEquals(createUserRequestDto.getFullName(), result.getFullName());
        assertEquals(createUserRequestDto.getPassword(), result.getPassword());
        assertEquals(createUserRequestDto.getRole(), result.getRole());
        assertEquals(createUserRequestDto.getIsActive(), result.getIsActive());
        assertEquals(createUserRequestDto.getProfilePicturePath(), result.getProfilePicturePath());
    }

}
