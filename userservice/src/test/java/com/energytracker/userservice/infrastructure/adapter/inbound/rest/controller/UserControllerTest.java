package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
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
                createUserRequestRestDto.getPassword(),
                createUserRequestRestDto.getRole(),
                createUserRequestRestDto.getIsActive(),
                LocalDate.now(),
                LocalDate.now(),
                createUserRequestRestDto.getProfilePicturePath()
        );

        when(createUserUseCase.createUser(any())).thenReturn(userResponseDto);

        mockMvc.perform(post("/api/v1/users")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequestRestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(createUserRequestRestDto.getEmail()))
                .andExpect(jsonPath("$.fullName").value(createUserRequestRestDto.getFullName()))
                .andExpect(jsonPath("$.password").value(createUserRequestRestDto.getPassword()))
                .andExpect(jsonPath("$.role").value(createUserRequestRestDto.getRole()))
                .andExpect(jsonPath("$.isActive").value(createUserRequestRestDto.getIsActive()))
                .andExpect(jsonPath("$.profilePicturePath").value(createUserRequestRestDto.getProfilePicturePath()));
    }

}
