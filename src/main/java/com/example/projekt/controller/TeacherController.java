package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<Teacher> addTeacher(@RequestBody UserDto teacherData) {
        return ResponseEntity.ok(teacherService.addTeacher(teacherData.toEntity(Teacher.class)));
    }

}
