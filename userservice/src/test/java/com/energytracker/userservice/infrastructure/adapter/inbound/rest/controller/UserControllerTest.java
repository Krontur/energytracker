package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.service.UserService;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.config.TestSecurityConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("mockedPassword");
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testCreateUser() throws Exception {
        CreateUserRequestRestDto createUserRequestRestDto = CreateUserRequestRestDto.builder()
                .email("email@email.com")
                .fullName("Test User")
                .password("password")
                .role("USER")
                .isActive(true)
                .profilePicturePath("profile.jpg")
                .build();

        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                createUserRequestRestDto.getEmail(),
                createUserRequestRestDto.getFullName(),
                null,
                createUserRequestRestDto.getRole(),
                createUserRequestRestDto.getIsActive(),
                LocalDate.now(),
                LocalDate.now(),
                createUserRequestRestDto.getProfilePicturePath()
        );

        when(userService.createUser(any())).thenReturn(userResponseDto);

        mockMvc.perform(post("/api/v1/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestRestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.email").value(createUserRequestRestDto.getEmail()))
                        .andExpect(jsonPath("$.fullName").value(createUserRequestRestDto.getFullName()))
                        .andExpect(jsonPath("$.role").value(createUserRequestRestDto.getRole()))
                        .andExpect(jsonPath("$.isActive").value(createUserRequestRestDto.getIsActive()))
                        .andExpect(jsonPath("$.profilePicturePath").value(createUserRequestRestDto.getProfilePicturePath()));
    }

}
