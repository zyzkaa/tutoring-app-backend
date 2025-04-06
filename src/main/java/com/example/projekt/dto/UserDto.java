package com.example.projekt.dto;

import java.sql.Date;

public record UserDto(String username,
               String password,
               String email,
               String firstName,
               String lastName,
               Date birthDate) {

}
