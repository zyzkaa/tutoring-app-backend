package com.example.projekt.dto.response;

import com.example.projekt.model.User;

import java.sql.Date;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName,
        Date birthDate
){
    public UserResponseDto(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate());
    }
}
