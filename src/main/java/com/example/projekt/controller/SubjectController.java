package com.example.projekt.controller;

import com.example.projekt.dto.response.TeacherWithRatingAndPrice;
import com.example.projekt.model.SubjectDict;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.SubjectService;
import com.example.projekt.service.TeacherService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDict>> getSubjects() {
        return ResponseEntity.ok(subjectService.getSubjects());
    }
}

