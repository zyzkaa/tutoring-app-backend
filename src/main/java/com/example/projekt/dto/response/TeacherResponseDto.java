package com.example.projekt.dto.response;

import com.example.projekt.model.Location;
import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;

import java.util.List;


public record TeacherResponseDto (
        String description,
        List<SubjectDetails> subjects,
        List<Location> locations,
        UserResponseDto user
){
    public TeacherResponseDto (Teacher teacher){
        this(teacher.getDescription(), teacher.getSubjectDetails(), teacher.getLocations(), new UserResponseDto(teacher));
    }
}
