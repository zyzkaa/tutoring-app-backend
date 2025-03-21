package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody UserDto userData) {
        return ResponseEntity.ok(userService.createUser(userData.toEntity(User.class)));
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
