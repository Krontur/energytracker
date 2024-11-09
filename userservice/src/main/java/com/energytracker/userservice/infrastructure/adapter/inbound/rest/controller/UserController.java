package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserRestDto;
import com.energytracker.userservice.application.service.UserService;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserRestDto> createUser(@RequestBody UserRestDto userRestDto) {
        UserDto createdUser = createUserUseCase.createUser(UserRestMapper.fromRestDtoToDto(userRestDto));
        return new ResponseEntity<>(UserRestMapper.fromDtoToRestDto(createdUser), HttpStatus.CREATED);
    }

}
