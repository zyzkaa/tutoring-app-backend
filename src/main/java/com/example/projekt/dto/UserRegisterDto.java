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

    public <U extends User> U toEntity(Class<U> entityClass) {
        try {
            U result = entityClass.getDeclaredConstructor().newInstance();

            BeanUtils.copyProperties(this, result);

//            result.setUsername(this.username);
//            result.setPassword(this.password);
//            result.setEmail(this.email);
//            result.setFirstName(this.firstName);
//            result.setLastName(this.lastName);
//            result.setBirthDate(this.birthDate);

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
