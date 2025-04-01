package com.example.projekt.service;

import com.example.projekt.dto.EditUserInfoDto;
import com.example.projekt.dto.UserRegisterDto;
import com.example.projekt.exception.UsernameAlreadyExistsException;
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

    public User createUser(UserRegisterDto userData) {
        if(userRepository.existsByUsername(userData.getUsername())) {
            throw new UsernameAlreadyExistsException(userData.getUsername());
        }
        return userRepository.save(PasswordHelper.encodePassword(new User(userData)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public static String[] returnNullArgs(User user, EditUserInfoDto userInfo) throws IllegalAccessException {
        var nullArgsList = new ArrayList<String>();
        for(Field field : userInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(userInfo) == null){
                nullArgsList.add(field.getName());
            }
        }
        return nullArgsList.toArray(new String[nullArgsList.size()]);
    }

    public User editUserInfo(User user, EditUserInfoDto editUserInfoDto) throws IllegalAccessException {
        BeanUtils.copyProperties(editUserInfoDto, user, returnNullArgs(user, editUserInfoDto));
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
    }
}
