package com.example.projekt.dto.response;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.Payment;
import com.example.projekt.model.PinnedStudent;
import com.example.projekt.model.Teacher;

import java.util.List;

public record TeacherProfileResponseDto(
        Long ratingCount,
        Long lessonsCount,
        Double moneyAmount,
        Long studentsCount,
        List<PinnedStudentResponseDto> students,
        List<LessonSlot> todayLessons,
        List<PaymentResponseDto> recentPayments,
        TeacherResponseDto teacher
) {

}
