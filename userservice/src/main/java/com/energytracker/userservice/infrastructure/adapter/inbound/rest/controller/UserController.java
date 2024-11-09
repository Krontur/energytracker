package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserResponseRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @GetMapping("/")
    public String HomePageTest(){
        return "Home Page Test";
    }

    @PostMapping
    public ResponseEntity<UserResponseRestDto> createUser(@RequestBody CreateUserRequestRestDto createUserRequestRestDto) {
        UserResponseDto createdUser = createUserUseCase.createUser(UserRestMapper.createUserRequestFromRestDtoToDto(createUserRequestRestDto));
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(createdUser), HttpStatus.CREATED);
    }

}
