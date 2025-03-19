package com.example.projekt.controller;

import com.example.projekt.dto.UserDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody UserDto teacher) {
        return ResponseEntity.ok(teacherService.addTeacher(teacher.toTeacherEntity()));
    }
}
