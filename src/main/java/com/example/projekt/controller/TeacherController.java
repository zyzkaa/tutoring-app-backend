package com.example.projekt.controller;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.TeacherFilter;
import com.example.projekt.dto.response.TeacherProfileResponseDto;
import com.example.projekt.dto.response.TeacherResponseDto;
import com.example.projekt.dto.response.TeacherSearchResponseDto;
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

    @GetMapping("/{username}") // make it return all profile data
    public ResponseEntity<TeacherResponseDto> getTeacherInfo(@PathVariable String username) {
        return ResponseEntity.ok(new TeacherResponseDto(teacherService.getByUsername(username)));
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
