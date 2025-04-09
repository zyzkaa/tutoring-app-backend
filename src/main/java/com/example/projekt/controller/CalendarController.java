package com.example.projekt.controller;

import com.example.projekt.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final GoogleCalendarService googleCalendarService;

    @GetMapping("/add")
    public ResponseEntity<String> addToGoogleCalendar(Authentication authentication) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated with OAuth2");
        }

        googleCalendarService.addLessonsToCalendar((OAuth2AuthenticationToken) authentication);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
