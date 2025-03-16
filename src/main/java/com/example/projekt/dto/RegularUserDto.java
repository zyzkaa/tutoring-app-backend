package com.example.projekt.dto;

import com.example.projekt.model.RegularUser;

import java.sql.Date;

public class RegularUserDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public RegularUser toEntity(){
        return RegularUser.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .build();
    }
}
