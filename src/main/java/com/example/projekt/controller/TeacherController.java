package com.example.projekt.controller;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.TeacherFilter;
import com.example.projekt.dto.response.*;
import com.example.projekt.model.SchoolPrice;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/me")
    public ResponseEntity<TeacherResponseDto> editTeacherInfo(@RequestBody TeacherDetailsDto teacherDetailsDto, @AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.addDetails(teacherDetailsDto, teacher, request)));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/me")
    public ResponseEntity<TeacherProfileResponseDto> getCurrentTeacherInfo(@AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
        return ResponseEntity.ok(teacherService.getTeacherProfile(teacher));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.getById(id)));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/students")
    public ResponseEntity<List<CompletedLessonResponseDto>> getAllStudents(@AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
        return ResponseEntity.ok(teacherService.findAllCompletedLessons(teacher).stream().map(CompletedLessonResponseDto::new)
                .collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<List<TeacherSearchResponseDto>> findTeachers(@RequestBody TeacherFilter teacherFilter) {
        return ResponseEntity.ok(teacherService.findTeachers(teacherFilter)
                .stream().map(
                        teacher -> {
                            var teacherEntity = teacher.teacher();
                            Double price = null;
                            if(teacherFilter.subjectId() == null || teacherFilter.schoolId() == null) {
                                var prices = teacherEntity.getSubjectDetails().stream()
                                        .flatMap(subjectDetails -> subjectDetails.getSchoolPrices().stream()
                                                .map(schoolPrice -> schoolPrice.getPrice()
                                                )).collect(Collectors.toSet());
                                price = prices.stream().min(Comparator.naturalOrder()).orElse(null);
                            } else {
                                price = teacherEntity.getSubjectDetails().getFirst().getSchoolPrices().getFirst().getPrice();
                            }

                            return new TeacherSearchResponseDto(
                                    teacherEntity.getFirstName(),
                                    teacherEntity.getLastName(),
                                    teacherEntity.getId(),
                                    price,
                                    teacher.avgRating(),
                                    teacherEntity.getDescription(),
                                    teacher.ratingCount()
                                    );
                        }
                ).collect(Collectors.toList()));
    }
}
