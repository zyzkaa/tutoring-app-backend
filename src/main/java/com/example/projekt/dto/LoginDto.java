package com.example.projekt.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class LoginDto {
    private String username;
    private String password;
}
