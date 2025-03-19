package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.model.RegularUser;
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
    public ResponseEntity<User> addRegularUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.createUser(user.toEntity()));
    }

    @GetMapping("/test")
    public List<RegularUser> test() {
        return userService.getAllUsers();
    }
}
