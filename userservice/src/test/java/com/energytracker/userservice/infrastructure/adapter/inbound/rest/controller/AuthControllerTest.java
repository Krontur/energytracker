package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.RegisterRequestDto;
import com.energytracker.userservice.application.dto.TokenResponseDto;
import com.energytracker.userservice.application.service.AuthService;
import com.energytracker.userservice.config.TestSecurityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        when(authService.register(any())).thenReturn(new TokenResponseDto("token", "refreshToken"));

        try {
            mockMvc.perform(post("/api/v1/auth/register")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new RegisterRequestDto("test", "test", "test"))))
                            .andExpect(status().isCreated())
                            .andExpect(result -> result.getResponse().getContentAsString().contains("token"))
                            .andExpect(result -> result.getResponse().getContentAsString().contains("refreshToken"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLogin() {
        when(authService.login(any())).thenReturn(new TokenResponseDto("token", "refreshToken"));

        try {
            mockMvc.perform(post("/api/v1/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new RegisterRequestDto("test", "test", "test"))))
                            .andExpect(status().isOk())
                            .andExpect(result -> result.getResponse().getContentAsString().contains("token"))
                            .andExpect(result -> result.getResponse().getContentAsString().contains("refreshToken"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRefreshToken() {
        when(authService.refreshToken(any())).thenReturn(new TokenResponseDto("token", "refreshToken"));

        try {
            mockMvc.perform(post("/api/v1/auth/refresh")
                            .with(csrf())
                            .header("Authorization", "Bearer token"))
                    .andExpect(status().isOk())
                    .andExpect(result -> result.getResponse().getContentAsString().contains("token"))
                    .andExpect(result -> result.getResponse().getContentAsString().contains("refreshToken"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
