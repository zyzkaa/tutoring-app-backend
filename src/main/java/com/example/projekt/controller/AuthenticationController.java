package com.example.projekt.controller;

import com.example.projekt.dto.LoginDto;
import com.example.projekt.dto.UserDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.dto.response.UserResponseDto;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.service.TeacherService;
import com.example.projekt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpSession session) throws Exception {
        return authenticationHelper.login(loginDto.email(), loginDto.password(), request, session);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/set_teacher")
    public String setTeacher(HttpSession session) {
        session.setAttribute("register_role", "user");
        return "success";
    }

    @GetMapping("/set_user")
    public String set(HttpSession session) {
        session.setAttribute("register_role", "teacher");
        return "success";
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserDto userData, HttpServletRequest request, HttpSession session) throws Exception {
        var user = userService.createUser(userData);
        authenticationHelper.login(userData.email(), userData.password(), request, session);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody UserDto teacherRegisterDto, HttpServletRequest request, HttpSession session) throws Exception {
        var teacher = teacherService.addTeacher(teacherRegisterDto);
        authenticationHelper.login(teacherRegisterDto.email(), teacherRegisterDto.password(), request, session);
        return ResponseEntity.ok(new TeacherResponseDto(teacher));
    }

    @GetMapping("/test") // delete this
    public String test(@AuthenticationPrincipal User user) {
        return user.getEmail() + " " + user.getAttributes().toString();
    }

    @GetMapping("/role")
    public String getRole(@AuthenticationPrincipal User user) {
        return user.getAuthorities().toString();
    }
}
