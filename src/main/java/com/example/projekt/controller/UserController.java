package com.example.projekt.controller;

import com.example.projekt.dto.UserRegisterDto;
import com.example.projekt.dto.UserResponseDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRegisterDto userData, HttpServletRequest request) {
        var response = ResponseEntity.ok(userService.createUser(userData.toEntity(User.class)));
        authenticationHelper.login(userData.getUsername(), userData.getPassword(), request);
        return response;
    }

    @GetMapping("/getAll")
    public List<User> test() {
        var users = userService.getAllUsers();
        users.forEach(user -> {
            if (user instanceof Teacher) {
                System.out.println("Teacher");
            } else {
                System.out.println("User");
            }
            System.out.println(user.getUsername());
        });
        return users;
    }
}
