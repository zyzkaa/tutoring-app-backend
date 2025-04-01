package com.example.projekt.controller;

import com.example.projekt.dto.response.RatingResponseDto;
import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.dto.UserDto;
import com.example.projekt.model.Rating;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.RatingService;
import com.example.projekt.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final AuthenticationHelper authenticationHelper;
    private final RatingService ratingService;

    @PostMapping("/register")
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody UserDto teacherRegisterDto, HttpServletRequest request) { // can be changed to contain user reg. dto and teacher details dto
        var teacher = teacherService.addTeacher(teacherRegisterDto);
        authenticationHelper.login(teacherRegisterDto.getUsername(), teacherRegisterDto.getPassword(), request);
        return ResponseEntity.ok(new TeacherResponseDto(teacher));
    }

    @GetMapping("/get-all") // delete or edit this
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping("/details")
    public ResponseEntity<TeacherResponseDto> addTeacherDetails(@RequestBody TeacherDetailsDto teacherDetailsDto, @AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.addDetails(teacherDetailsDto, teacher, request)));
    }

    @PutMapping("/details")
    public ResponseEntity<TeacherResponseDto> changeTeacherDetails(@RequestBody TeacherDetailsDto teacherDetailsDto, @AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.addDetails(teacherDetailsDto, teacher, request)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<TeacherResponseDto> getTeacher(@PathVariable String username) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.getByUsername(username)));
    }

    @GetMapping("/{username}/ratings")
    public ResponseEntity<List<RatingResponseDto>> getTeacherRatings(@PathVariable String username) {
        var ratings = ratingService.getRatingsByTeacherUsername(username);
        var ratingResponseDtos = new ArrayList<RatingResponseDto>();

        for(Rating rating : ratings){
            ratingResponseDtos.add(new RatingResponseDto(rating));
        }
        return ResponseEntity.ok(ratingResponseDtos);
    }
}
