package com.example.projekt.dto.response;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.SubjectDict;

import java.util.UUID;

public record CompletedLessonResponseDto(
    UUID studentID,
    String firstName,
    String lastName,
    SubjectDict subject,
    double price
) {
    public CompletedLessonResponseDto(LessonSlot lessonSlot){
        this(
                lessonSlot.getStudent().getId(),
                lessonSlot.getStudent().getFirstName(),
                lessonSlot.getStudent().getLastName(),
                lessonSlot.getSubject(),
                lessonSlot.getPrice());
    }
}
