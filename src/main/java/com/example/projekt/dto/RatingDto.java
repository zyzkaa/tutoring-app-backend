package com.example.projekt.dto;

import org.hibernate.validator.constraints.Range;

public record RatingDto(
        @Range(min = 0, max = 5)
        int value,
        String teacherId,
        String content
){}
