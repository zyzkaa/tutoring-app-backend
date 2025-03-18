package com.example.projekt.dto;

import com.example.projekt.model.RegularUser;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@ToString
@RequiredArgsConstructor
public class RegularUserDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;


    public RegularUser toEntity(){
        RegularUser newUser =  RegularUser.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .build();

        System.out.println(newUser);
        return newUser;
    }
}
