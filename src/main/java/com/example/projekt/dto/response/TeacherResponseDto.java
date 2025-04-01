package com.example.projekt.dto.response;

import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class TeacherResponseDto extends UserResponseDto {
    private String description;
    private List<SubjectDetails> subjects;

    public TeacherResponseDto(Teacher teacher) {
        super(teacher);
        this.description = teacher.getDescription();
        this.subjects = teacher.getSubjectDetails();
    }
}
