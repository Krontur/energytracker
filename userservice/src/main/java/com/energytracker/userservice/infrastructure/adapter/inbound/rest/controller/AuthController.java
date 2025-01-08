package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.LoginRequestDto;
import com.energytracker.userservice.application.dto.RegisterRequestDto;
import com.energytracker.userservice.application.dto.TokenResponseDto;
import com.energytracker.userservice.application.port.inbound.AuthenticateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.origin.url}")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        TokenResponseDto tokenResponseDto = authenticateUserUseCase.register(registerRequestDto);
        if (tokenResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = authenticateUserUseCase.login(loginRequestDto);
        log.info("Token response: {}", tokenResponseDto);
        if (tokenResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        TokenResponseDto refreshedToken = authenticateUserUseCase.refreshToken(authorizationHeader);
        if (refreshedToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
    }

}
