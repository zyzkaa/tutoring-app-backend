package com.example.projekt.controller;

import com.example.projekt.dto.LoginDto;
import com.example.projekt.dto.UserDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.dto.response.UserResponseDto;
import com.example.projekt.model.User;
import com.example.projekt.service.TeacherService;
import com.example.projekt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final TeacherService teacherService;
//    private final AuthenticationConfiguration authenticationConfiguration;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request) throws Exception {
//        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationHelper.login(loginDto.getUsername(), loginDto.getPassword(), request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserDto userData, HttpServletRequest request) {
        var user = userService.createUser(userData);
        authenticationHelper.login(userData.getUsername(), userData.getPassword(), request);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody UserDto teacherRegisterDto, HttpServletRequest request) { // can be changed to contain user reg. dto and teacher details dto
        var teacher = teacherService.addTeacher(teacherRegisterDto);
        authenticationHelper.login(teacherRegisterDto.getUsername(), teacherRegisterDto.getPassword(), request);
        return ResponseEntity.ok(new TeacherResponseDto(teacher));
    }

    @GetMapping("/test") // delete this
    public String test(@AuthenticationPrincipal User user) {
        return user.getUsername();
    }
}
