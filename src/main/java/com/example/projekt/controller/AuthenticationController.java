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
//    private final AuthenticationConfiguration authenticationConfiguration;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpSession session) throws Exception {
//        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        session.setAttribute("flow", "login");
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

    @GetMapping("/reg")
    public String reg(HttpSession session) {
        session.setAttribute("register_role", "user");
        return "success";
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserDto userData, HttpServletRequest request, HttpSession session) throws Exception {
//        session.setAttribute("register_role", "user");
        var user = userService.createUser(userData);
        authenticationHelper.login(userData.email(), userData.password(), request, session);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody UserDto teacherRegisterDto, HttpServletRequest request, HttpSession session) throws Exception {
//        session.setAttribute("register_role", "teacher");
        var teacher = teacherService.addTeacher(teacherRegisterDto);
        authenticationHelper.login(teacherRegisterDto.email(), teacherRegisterDto.password(), request, session);
        return ResponseEntity.ok(new TeacherResponseDto(teacher));
    }

    @GetMapping("/test") // delete this
    public String test(@AuthenticationPrincipal User user) {
        return user.getEmail() + " " + user.getAttributes().toString();
    }
//
//    @GetMapping("/oauth2/login")
//    public void loginAfterOAuth2(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
//        String email = oAuth2User.getAttribute("email");
//        if(userService.existsByEmail(email)){
//            login(new LoginDto(email), request, session);
//        } else if(session.getAttribute("register_role").equals("user")) {
//            addUser(new UserDto(email), request, session);
//        } else {
//            addTeacher(new UserDto(email), request, session);
//        }
//        response.sendRedirect("/auth/test");
//    }
}
