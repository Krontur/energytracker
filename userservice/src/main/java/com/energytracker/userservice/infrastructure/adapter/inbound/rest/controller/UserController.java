package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.inbound.GetAllUsersUseCase;

import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserResponseRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetAllUsersUseCase getAllUsersUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseRestDto>> getAllUsers() {
        List<UserResponseDto> users = getAllUsersUseCase.getAllUsers();
        List<UserResponseRestDto> userResponseRestDtos = new ArrayList<UserResponseRestDto>();
        users.forEach(user -> userResponseRestDtos.add(UserRestMapper.userResponseFromDtoToRestDto(user)));
        return new ResponseEntity<>(userResponseRestDtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserResponseRestDto> createUser(@RequestBody CreateUserRequestRestDto createUserRequestRestDto) {
        UserResponseDto createdUser = createUserUseCase.createUser(UserRestMapper.createUserRequestFromRestDtoToDto(createUserRequestRestDto));
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(createdUser), HttpStatus.CREATED);
    }

}
