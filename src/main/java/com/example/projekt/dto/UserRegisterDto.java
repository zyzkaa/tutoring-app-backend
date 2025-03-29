package com.example.projekt.dto;

import com.example.projekt.model.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.sql.Date;

@Setter
@ToString
@Getter
@RequiredArgsConstructor
public class UserRegisterDto {
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected Date birthDate;
}
