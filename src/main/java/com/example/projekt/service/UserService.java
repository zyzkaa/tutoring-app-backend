package com.example.projekt.service;

import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(PasswordHelper.encodePassword(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
