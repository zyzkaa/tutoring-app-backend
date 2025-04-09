package com.example.projekt.dto;

public record LoginDto(
        String email,
        String password
){
    public LoginDto(String email) {
        this(email, "");
    }
}
