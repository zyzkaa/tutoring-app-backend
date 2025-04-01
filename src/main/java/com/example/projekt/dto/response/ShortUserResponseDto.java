package com.example.projekt.dto.response;

import com.example.projekt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShortUserResponseDto {
    private UUID id;
    private String username;
    private String role;

    public ShortUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getAuthorities().toString();
    }
}
