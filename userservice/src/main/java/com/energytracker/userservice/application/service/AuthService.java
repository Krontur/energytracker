package com.energytracker.userservice.application.service;

import com.energytracker.userservice.application.dto.*;
import com.energytracker.userservice.application.mapper.UserMapper;
import com.energytracker.userservice.application.port.inbound.AuthenticateUserUseCase;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.inbound.GetUserByEmailUseCase;
import com.energytracker.userservice.application.port.outbound.TokenRepositoryPort;
import com.energytracker.userservice.domain.model.Token;
import com.energytracker.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticateUserUseCase {

    private final TokenRepositoryPort tokenRepositoryPort;
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        User user = getUserByEmailUseCase.getUserByEmail(loginRequestDto.getEmail());
        if (user == null) {
            return null;
        }
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        jwtService.saveToken(token, user);
        return new TokenResponseDto(token, refreshToken);
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public boolean isValidToken(String token) {
        return false;
    }

    @Override
    public TokenResponseDto register(RegisterRequestDto registerRequestDto) {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(
                registerRequestDto.getEmail(),
                registerRequestDto.getFullName(),
                registerRequestDto.getPassword(),
                "USER",
                false,
                ""
        );
        UserResponseDto userResponseDto = createUserUseCase.createUser(createUserRequestDto);
        if (userResponseDto == null) {
            return null;
        }
        User user = UserMapper.userResponseDtoToDomain(userResponseDto);
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        jwtService.saveToken(token, user);
        return new TokenResponseDto(token, refreshToken);

    }

    @Override
    public TokenResponseDto refreshToken(String authorizationHeader) {
        return null;
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepositoryPort.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getUserAccountId());
        if(!validUserTokens.isEmpty()) {
            for(Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepositoryPort.saveAll(validUserTokens);
        }
    }

}
