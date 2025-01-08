package com.energytracker.userservice.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.userservice.application.dto.UserResponseDto;

import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.inbound.GetAllUsersUseCase;
import com.energytracker.userservice.application.port.inbound.GetUserByIdUseCase;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.UserResponseRestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "${cors.origin.url}")
@RequiredArgsConstructor
public class UserController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final CreateUserUseCase createUserUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseRestDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = getUserByIdUseCase.getUserById(userId);
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseRestDto>> getAllUsers() {
        log.info("Getting all users");
        List<UserResponseDto> users = getAllUsersUseCase.getAllUsers();
        List<UserResponseRestDto> userResponseRestDtos = new ArrayList<UserResponseRestDto>();
        users.forEach(user -> userResponseRestDtos.add(UserRestMapper.userResponseFromDtoToRestDto(user)));
        return new ResponseEntity<>(userResponseRestDtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserResponseRestDto> createUser(@RequestBody CreateUserRequestRestDto createUserRequestRestDto) {
        UserResponseDto createdUser = createUserUseCase.createUser(UserRestMapper.createUserRequestFromRestDtoToDto(
                createUserRequestRestDto));
        if (createdUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserRestMapper.userResponseFromDtoToRestDto(createdUser), HttpStatus.CREATED);
    }

}
