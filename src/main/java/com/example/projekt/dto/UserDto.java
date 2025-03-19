package com.example.projekt.dto;

import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import lombok.*;

import java.lang.reflect.InvocationTargetException;
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

    public <U extends User> U toEntity(Class<U> entityClass) {
        try {
            U result = entityClass.getDeclaredConstructor().newInstance();

            result.setUsername(this.username);
            result.setPassword(this.password);
            result.setEmail(this.email);
            result.setFirstName(this.firstName);
            result.setLastName(this.lastName);
            result.setBirthDate(this.birthDate);
            result.setCreationDate(new Timestamp(System.currentTimeMillis()));

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
