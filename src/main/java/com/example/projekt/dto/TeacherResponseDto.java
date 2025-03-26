package com.example.projekt.dto;

import com.example.projekt.model.Teacher;
import lombok.Data;

@Data
public class TeacherResponseDto extends UserResponseDto {
    private String description;

    public TeacherResponseDto(Teacher teacher) {
        super(teacher);
        this.description = teacher.getDescription();
    }
}
