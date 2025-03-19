package com.example.projekt.dto;

import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@ToString
@RequiredArgsConstructor
public class UserDto {
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected Date birthDate;

    private <B extends User.UserBuilder<?, ?>> B fillBuilder(B builder) {
        return (B) builder
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .creationDate(new Timestamp(System.currentTimeMillis()));
    }


    public User toUserEntity(){
        return fillBuilder(User.builder()).build();
    }

    public Teacher toTeacherEntity(){
        return fillBuilder(Teacher.builder()).build();
    }

}
