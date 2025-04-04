package com.example.projekt.dto.response;

import com.example.projekt.model.Rating;
import com.example.projekt.model.SubjectDict;
import lombok.Data;

import java.util.List;

@Data
public class RatingResponseDto {
    private int id;

    private int value;

    private String content;

    private ShortUserResponseDto user ;

    private ShortUserResponseDto teacher;

    private List<SubjectDict> subjectDict;

    public RatingResponseDto(Rating rating) {
        this.id = rating.getId();
        this.value = rating.getValue();
        this.content = rating.getContent();
        this.user = new ShortUserResponseDto(rating.getUser());
        this.teacher = new ShortUserResponseDto(rating.getTeacher());
        this.subjectDict = rating.getSubjectDict();
    }
}
