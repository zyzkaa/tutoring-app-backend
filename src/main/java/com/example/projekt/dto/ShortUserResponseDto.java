package com.example.projekt.dto;

import com.example.projekt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShortUserResponseDto {
    private UUID id;
    private String username;

    public ShortUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
