package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;

import com.energytracker.userservice.application.service.UserService;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserResponseRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper.UserRestMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseRestDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseRestDto>> getAllUsers() {
        log.info("Getting all users");
        List<UserResponseDto> users = userService.getAllUsers();
        List<UserResponseRestDto> userResponseRestDtos = new ArrayList<UserResponseRestDto>();
        users.forEach(user -> userResponseRestDtos.add(UserRestMapper.userResponseFromDtoToRestDto(user)));
        return new ResponseEntity<>(userResponseRestDtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserResponseRestDto> createUser(@RequestBody CreateUserRequestRestDto createUserRequestRestDto) {
        UserResponseDto createdUser = userService.createUser(UserRestMapper.createUserRequestFromRestDtoToDto(
                createUserRequestRestDto));
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(createdUser), HttpStatus.CREATED);
    }

}
