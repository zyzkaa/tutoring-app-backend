package com.example.projekt.service;

import com.example.projekt.dto.UserResponseDto;
import com.example.projekt.exception.UsernameAlreadyExistsException;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto createUser(User userData) {
        if(userRepository.existsByUsername(userData.getUsername())) {
            throw new UsernameAlreadyExistsException(userData.getUsername());
        }
        var user = userRepository.save(PasswordHelper.encodePassword(userData));
        return new UserResponseDto(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
