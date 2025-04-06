package com.example.projekt.controller;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.response.TeacherProfileResponseDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.model.Teacher;
import com.example.projekt.service.RatingService;
import com.example.projekt.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/{subject}")
    public ResponseEntity<List<Teacher>> getTeachersBySubject(@PathVariable String subject, @RequestParam(required = false) List<String> localization) {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

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

    @GetMapping("/{username}") // make it return all profile data
    public ResponseEntity<TeacherResponseDto> getTeacherInfo(@PathVariable String username) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.getByUsername(username)));
    }

//    @PreAuthorize("hasRole('TEACHER')")
//    @PostMapping("/students/{id}")
//    public ResponseEntity<Void> addStudentToList(@PathVariable UUID id, @AuthenticationPrincipal Teacher teacher){
//        teacherService.addStudentToList(id, teacher);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @PreAuthorize("hasRole('TEACHER')")
//    @DeleteMapping("/students/{id}")
//    public ResponseEntity<Void> removeStudentFromList(@PathVariable UUID id, @AuthenticationPrincipal Teacher teacher){
//        teacherService.addStudentToList(id, teacher);
//        return ResponseEntity.ok().build();
//    }


//    @GetMapping("/{username}/ratings")
//    public ResponseEntity<List<RatingResponseDto>> getTeacherRatings(@PathVariable String username) {
//        var ratings = ratingService.getRatingsByTeacherUsername(username);
//        var ratingResponseDtos = new ArrayList<RatingResponseDto>();
//
//        for(Rating rating : ratings){
//            ratingResponseDtos.add(new RatingResponseDto(rating));
//        }
//        return ResponseEntity.ok(ratingResponseDtos);
//    }

    //    @PostMapping("/me")
//    public ResponseEntity<TeacherResponseDto> addTeacherDetails(@RequestBody TeacherDetailsDto teacherDetailsDto, @AuthenticationPrincipal Teacher teacher, HttpServletRequest request) {
//        return ResponseEntity.ok(new TeacherResponseDto(teacherService.addDetails(teacherDetailsDto, teacher, request)));
//    }
}
