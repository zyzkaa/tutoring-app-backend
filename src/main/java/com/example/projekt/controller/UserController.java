package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.model.User;
import com.example.projekt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserDto userData) {
        return ResponseEntity.ok(userService.createUser(userData.toEntity(User.class)));
    }

    @GetMapping("/getAll")
    public List<User> test() {
        return userService.getAllUsers();
    }
}
