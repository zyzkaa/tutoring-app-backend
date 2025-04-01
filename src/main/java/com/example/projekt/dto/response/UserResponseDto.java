package com.example.projekt.dto.response;

import com.example.projekt.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
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
        BeanUtils.copyProperties(user, this);
//        this.id = user.getId();
//        this.username = user.getUsername();
//        this.email = user.getEmail();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//        this.birthDate = user.getBirthDate();
    }
}
