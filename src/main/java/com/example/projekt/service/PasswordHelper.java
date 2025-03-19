package com.example.projekt.service;

import com.example.projekt.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHelper {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static <T extends User> T encodePassword(T user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }
}
