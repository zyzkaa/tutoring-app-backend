package com.example.projekt.controller;

import com.example.projekt.dto.LessonSlotDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.service.LessonService;
import com.example.projekt.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final TeacherService teacherService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<String> addSlots(@RequestBody List<LessonSlotDto> lessonSlotDto, @AuthenticationPrincipal Teacher teacher) {
        lessonService.addLessonSlots(lessonSlotDto, teacher);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PatchMapping("/{id}") // mabye delete this????
//    public ResponseEntity<LessonSlot> bookSlot(@PathVariable Long id, @AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(lessonService.bookLessonSlot(id, user));
//    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/book/{subjectId}/{schoolId}")
    public ResponseEntity<List<LessonSlot>> bookSlots(@RequestBody List<Integer> idList, @AuthenticationPrincipal User user, @PathVariable int subjectId, @PathVariable int schoolId) {
        return ResponseEntity.ok(lessonService.bookLessonSlots(user, idList, subjectId, schoolId));
    }

    @PatchMapping("/{id}") // change to list of ids?
    public ResponseEntity<String> unbookSlot(@PathVariable Long id, @AuthenticationPrincipal User user) {
        lessonService.unbookLessonSlot(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // only for teacher, maybe change to list of ids
    public ResponseEntity<LessonSlot> cancelSlot(@PathVariable Long id, @AuthenticationPrincipal Teacher teacher) {
        return ResponseEntity.ok(lessonService.cancellLessonSlot(id, teacher));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{lessonId}/pin")
    public ResponseEntity<Void> pinStudent(@PathVariable Long lessonId, @AuthenticationPrincipal Teacher teacher) {
        teacherService.pinStudentFromLesson(lessonId, teacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{pinId}/pin")
    public ResponseEntity<Void> unpinStudent(@PathVariable Long pinId, @AuthenticationPrincipal Teacher teacher) {
        teacherService.unpinStudent(pinId, teacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
