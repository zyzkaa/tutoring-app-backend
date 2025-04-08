package com.example.projekt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

import java.util.List;

public record TeacherFilter(
        List<Long> locationId,
        Long subjectId,
        Long schoolId,
        Double minPrice,
        Double maxPrice,
        int page,
        int size
) {
}
