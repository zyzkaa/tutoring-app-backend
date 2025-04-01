package com.example.projekt.dto;

import lombok.*;

import java.sql.Date;

@Setter
@ToString
@Getter
@RequiredArgsConstructor
public class UserDto {
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected Date birthDate;
}
