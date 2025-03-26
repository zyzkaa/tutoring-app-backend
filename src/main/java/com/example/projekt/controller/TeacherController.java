package com.example.projekt.controller;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.TeacherResponseDto;
import com.example.projekt.dto.UserRegisterDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/register")
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody UserRegisterDto teacherRegisterDto, HttpServletRequest request) {
        var response = ResponseEntity.ok(teacherService.addTeacher(teacherRegisterDto.toEntity(Teacher.class)));
        authenticationHelper.login(teacherRegisterDto.getUsername(), teacherRegisterDto.getPassword(), request);
        return response;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping("/add-details")
    public ResponseEntity<TeacherResponseDto> addTeacherDetails(@RequestBody TeacherDetailsDto teacherDetailsDto) {
        return ResponseEntity.ok(teacherService.addDetails(teacherDetailsDto));
    }

//    @GetMapping("/get-with-data")
//    public ResponseEntity<Teacher> getWithAllData() {
//        return ResponseEntity.ok(teacherService.getWithAllData("asdasd"));
//    }

}
