package com.example.projekt.service;

import com.example.projekt.model.User;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHelper {
    @Getter
    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static <T extends User> T encodePassword(T user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }
}
