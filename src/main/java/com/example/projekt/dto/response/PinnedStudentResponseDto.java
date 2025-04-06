package com.example.projekt.dto.response;

import com.example.projekt.model.PinnedStudent;

public record PinnedStudentResponseDto(
        ShortUserResponseDto student,
        PinnedStudent pinnedStudent
) {
}
