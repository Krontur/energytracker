package com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.userservice.application.dto.CreateUserRequestDto;
import com.energytracker.userservice.application.dto.UserResponseDto;
import com.energytracker.userservice.infrastructure.adapter.outbound.persistence.sql.entity.UserEntity;
import org.junit.jupiter.api.Test;

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
    public void testUserResponseFromEntityToDto() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAccountId(1L);
        userEntity.setEmail("test@email.com");
        userEntity.setFullName("Test User");
        userEntity.setPassword("password");
        userEntity.setRole("USER");
        userEntity.setIsActive(true);
        userEntity.setProfilePicturePath("profile-picture.jpg");

        UserResponseDto userResponseDto = UserPersistenceMapper.userResponseEntityToDto(userEntity);

        assertEquals(userEntity.getUserAccountId(), userResponseDto.getUserAccountId());
        assertEquals(userEntity.getEmail(), userResponseDto.getEmail());
        assertEquals(userEntity.getFullName(), userResponseDto.getFullName());
        assertEquals(userEntity.getPassword(), userResponseDto.getPassword());
        assertEquals(userEntity.getRole(), userResponseDto.getRole());
        assertEquals(userEntity.getIsActive(), userResponseDto.getIsActive());
        assertEquals(userEntity.getProfilePicturePath(), userResponseDto.getProfilePicturePath());

    }

}
