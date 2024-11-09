package com.energytracker.userservice.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.infrastructure.adapter.inbound.rest.dto.CreateUserRequestRestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRestMapperTest {

    @Test
    public void testCreateUserRequestFromRestDtoToDto() {
        CreateUserRequestRestDto createUserRequestRestDto = CreateUserRequestRestDto.builder()
                .email("test@email.com")
                .fullName("Test User")
                .password("password")
                .role("USER")
                .isActive(true)
                .profilePicturePath("profile.jpg")
                .build();

        CreateUserRequestDto createUserRequestDto = UserRestMapper.createUserRequestFromRestDtoToDto(createUserRequestRestDto);

        assertEquals(createUserRequestRestDto.getEmail(), createUserRequestDto.getEmail());
        assertEquals(createUserRequestRestDto.getFullName(), createUserRequestDto.getFullName());
        assertEquals(createUserRequestRestDto.getPassword(), createUserRequestDto.getPassword());
        assertEquals(createUserRequestRestDto.getRole(), createUserRequestDto.getRole());
        assertEquals(createUserRequestRestDto.getIsActive(), createUserRequestDto.getIsActive());
        assertEquals(createUserRequestRestDto.getProfilePicturePath(), createUserRequestDto.getProfilePicturePath());

    }

}
