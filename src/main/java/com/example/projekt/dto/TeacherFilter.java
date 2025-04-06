package com.example.projekt.dto;

import java.util.List;

public record TeacherFilter(
        List<Long> locationId,
        Long subjectId,
        Long schoolId,
        Double minPrice,
        Double maxPrice
) {
}
