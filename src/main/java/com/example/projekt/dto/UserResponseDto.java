package com.example.projekt.dto;

import com.example.projekt.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
    }
}
