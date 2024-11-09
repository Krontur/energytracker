package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.CreateUserRequestPersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.dto.UserResponsePersistenceDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPersistenceMapperTest {

    @Test
    public void testCreateUserRequestFromDtoToEntity(){
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(
                "test@email.com",
                "Test User",
                "password",
                "USER",
                true,
                "profile-picture.jpg"
        );

        UserEntity userEntity = UserPersistenceMapper.createUserRequestFromDtoToEntity(createUserRequestDto);

        assertEquals(createUserRequestDto.getEmail(), userEntity.getEmail());
        assertEquals(createUserRequestDto.getFullName(), userEntity.getFullName());
        assertEquals(createUserRequestDto.getPassword(), userEntity.getPassword());
        assertEquals(createUserRequestDto.getRole(), userEntity.getRole());
        assertEquals(createUserRequestDto.getIsActive(), userEntity.getIsActive());
        assertEquals(createUserRequestDto.getProfilePicturePath(), userEntity.getProfilePicturePath());

    }

    @Test
    public void testUserResponseFromEntityToPersistenceDto() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAccountId(1L);
        userEntity.setEmail("test@email.com");
        userEntity.setFullName("Test User");
        userEntity.setPassword("password");
        userEntity.setRole("USER");
        userEntity.setIsActive(true);
        userEntity.setProfilePicturePath("profile-picture.jpg");

        UserResponsePersistenceDto userResponsePersistenceDto = UserPersistenceMapper.userResponseFromEntityToPersistenceDto(userEntity);

        assertEquals(userEntity.getUserAccountId(), userResponsePersistenceDto.getUserAccountId());
        assertEquals(userEntity.getEmail(), userResponsePersistenceDto.getEmail());
        assertEquals(userEntity.getFullName(), userResponsePersistenceDto.getFullName());
        assertEquals(userEntity.getPassword(), userResponsePersistenceDto.getPassword());
        assertEquals(userEntity.getRole(), userResponsePersistenceDto.getRole());
        assertEquals(userEntity.getIsActive(), userResponsePersistenceDto.getIsActive());
        assertEquals(userEntity.getProfilePicturePath(), userResponsePersistenceDto.getProfilePicturePath());

    }

    @Test
    public void testUserResponseFromPersistenceDtoToDto() {
        UserResponsePersistenceDto userResponsePersistenceDto = new UserResponsePersistenceDto(
                1L,
                "test@email.com",
                "Test User",
                "password",
                "USER",
                true,
                LocalDate.of(2021, 10, 10),
                LocalDate.of(2021, 10, 10),
                "profile-picture.jpg"
        );

        UserResponseDto userResponseDto = UserPersistenceMapper.userResponseFromPersistenceDtoToDto(userResponsePersistenceDto);

        assertEquals(userResponsePersistenceDto.getUserAccountId(), userResponseDto.getUserAccountId());
        assertEquals(userResponsePersistenceDto.getEmail(), userResponseDto.getEmail());
        assertEquals(userResponsePersistenceDto.getFullName(), userResponseDto.getFullName());
        assertEquals(userResponsePersistenceDto.getPassword(), userResponseDto.getPassword());
        assertEquals(userResponsePersistenceDto.getRole(), userResponseDto.getRole());
        assertEquals(userResponsePersistenceDto.getIsActive(), userResponseDto.getIsActive());
        assertEquals(userResponsePersistenceDto.getCreatedDate(), userResponseDto.getCreatedDate());
        assertEquals(userResponsePersistenceDto.getUpdatedDate(), userResponseDto.getUpdatedDate());
        assertEquals(userResponsePersistenceDto.getProfilePicturePath(), userResponseDto.getProfilePicturePath());

    }

    @Test
    public void testCreateUserRequestFromDtoToPersistenceDto() {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(
                "email@test.com",
                "Test User2",
                "password2",
                "USER",
                true,
                "profile-picture2.jpg"
        );

        CreateUserRequestPersistenceDto createUserRequestPersistenceDto = UserPersistenceMapper.createUserRequestFromDtoToPersistenceDto(createUserRequestDto);

        assertEquals(createUserRequestDto.getEmail(), createUserRequestPersistenceDto.getEmail());
        assertEquals(createUserRequestDto.getFullName(), createUserRequestPersistenceDto.getFullName());
        assertEquals(createUserRequestDto.getPassword(), createUserRequestPersistenceDto.getPassword());
        assertEquals(createUserRequestDto.getRole(), createUserRequestPersistenceDto.getRole());
        assertEquals(createUserRequestDto.getIsActive(), createUserRequestPersistenceDto.getIsActive());
        assertEquals(createUserRequestDto.getProfilePicturePath(), createUserRequestPersistenceDto.getProfilePicturePath());

    }
}
