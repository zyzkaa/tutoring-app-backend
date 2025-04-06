package com.example.projekt.dto.response;

import com.example.projekt.model.User;

import java.util.UUID;


public record ShortUserResponseDto(
        UUID id,
        String username, // change to first name?
        String role
){
    public ShortUserResponseDto(User user){
        this(user.getId(), user.getUsername(), user.getAuthorities().toString());
    }
}
