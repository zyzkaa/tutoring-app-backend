package com.example.projekt;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.LessonState;
import com.example.projekt.model.Payment;
import com.example.projekt.repository.LessonSlotRepository;
import com.example.projekt.repository.PaymentRepository;
import com.example.projekt.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonStatusScheduler {
    private final LessonSlotRepository lessonSlotRepository;
    private final PaymentRepository paymentRepository;

    @Scheduled(fixedRate = 60000)
    public void changeStatusToCompleted() {
        List<LessonSlot> lessons = lessonSlotRepository.findLessonSlotByStateAndDateAndTimeBefore(LessonState.BOOKED, LocalDate.now(), LocalTime.now().minusHours(1));

        for (LessonSlot lesson : lessons) {
            lesson.setState(LessonState.COMPLETED);
            paymentRepository.save(new Payment(lesson));
        }

        lessonSlotRepository.saveAll(lessons);
    }
}
