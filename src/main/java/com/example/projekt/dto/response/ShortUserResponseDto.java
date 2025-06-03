package com.example.projekt.dto.response;

import com.example.projekt.model.User;

import java.util.UUID;


public record ShortUserResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String role
){
    public ShortUserResponseDto(User user){
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getAuthorities().toString());
    }
}
