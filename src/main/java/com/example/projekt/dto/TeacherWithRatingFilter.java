package com.example.projekt.dto;

import com.example.projekt.model.Teacher;

public record TeacherWithRatingFilter(
        Teacher teacher,
        Double avgRating
) {
}
