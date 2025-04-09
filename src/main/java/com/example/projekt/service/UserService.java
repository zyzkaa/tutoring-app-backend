package com.example.projekt.service;

import com.example.projekt.dto.UserDto;
import com.example.projekt.exception.EmailAlreadyExistsException;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserDto userData) {
        if(userRepository.existsByEmail(userData.email())) {
            throw new EmailAlreadyExistsException(userData.email());
        }
        return userRepository.save(PasswordHelper.encodePassword(new User(userData)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public static String[] returnNullArgs(User user, UserDto userDto) throws IllegalAccessException {
        var nullArgsList = new ArrayList<String>();
        for(Field field : userDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(userDto) == null){
                nullArgsList.add(field.getName());
            }
        }
        return nullArgsList.toArray(new String[nullArgsList.size()]);
    }

    public User editUserInfo(UserDto userDto, User user) throws IllegalAccessException {
        BeanUtils.copyProperties(userDto, user, returnNullArgs(user, userDto));
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
