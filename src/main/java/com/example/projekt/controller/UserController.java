package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.dto.response.UserResponseDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping("/getAll") // do zmiany albo wywalenia
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

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> editUserInfo(@RequestBody UserDto userDto, @AuthenticationPrincipal User user) throws IllegalAccessException {
        return ResponseEntity.ok(new UserResponseDto(userService.editUserInfo(userDto, user)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUserInfo(@AuthenticationPrincipal User user) {
        return null;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(new UserResponseDto(userService.getUserByUsername(username)));
    }

    @DeleteMapping("/{id}") // usun to
    public ResponseEntity<UserResponseDto> deleteById(@PathVariable UUID id){
        return ResponseEntity.ok(new UserResponseDto(userService.deleteById(id)));
    }

}
