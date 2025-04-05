package com.example.projekt.controller;

import com.example.projekt.dto.LessonSlotDto;
import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.service.LessonService;
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

    @PostMapping("/add")
    public ResponseEntity<String> addSlots(@RequestBody List<LessonSlotDto> lessonSlotDto, @AuthenticationPrincipal Teacher teacher) {
        lessonService.addLessonSlots(lessonSlotDto, teacher);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}") // mabye delete this????
    public ResponseEntity<LessonSlot> bookSlot(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(lessonService.bookLessonSlot(id, user));
    }

    @PatchMapping("/book")
    public ResponseEntity<List<LessonSlot>> bookSlots(@RequestBody List<Long> idList, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(lessonService.bookLessonSlots(user, idList));
    }

    @DeleteMapping("/{id}") // change to list of ids?
    public ResponseEntity<String> unbookSlot(@PathVariable Long id, @AuthenticationPrincipal User user) {
        lessonService.unbookLessonSlot(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
