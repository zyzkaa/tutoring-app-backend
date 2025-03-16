package com.example.projekt.controller;

import com.example.projekt.dto.RegularUserDto;
import com.example.projekt.model.RegularUser;
import com.example.projekt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<RegularUser> addRegularUser(@RequestBody RegularUserDto user) {
        return ResponseEntity.ok(userService.createUser(user.toEntity()));
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
