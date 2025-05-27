package com.example.projekt.dto.response;

import java.util.UUID;

public record TeacherSearchResponseDto(
        String firstName,
        String lastName,
        UUID id,
        Double price,
        Double avgRating,
        String description,
        Long ratingCount
) {
}
