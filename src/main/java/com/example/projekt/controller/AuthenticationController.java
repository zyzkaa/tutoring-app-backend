package com.example.projekt.controller;

import com.example.projekt.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("zalogowano jako " + loginDto.getUsername());
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        if (request.isUserInRole("USER")){
            return "zalogowano jako user";
        } else if (request.isUserInRole("TEACHER")){
            return "zalogowano jako teacher";
        } else {
            return "zalogowano jako huj wie co";
        }
    }
}
