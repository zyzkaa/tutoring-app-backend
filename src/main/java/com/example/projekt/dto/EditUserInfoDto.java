package com.example.projekt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class EditUserInfoDto {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String description;
}
