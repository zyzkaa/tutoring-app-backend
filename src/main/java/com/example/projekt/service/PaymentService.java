package com.example.projekt.service;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.Payment;
import com.example.projekt.repository.PaymentRepository;
import com.example.projekt.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;
    public final TeacherRepository teacherRepository;

    public Payment createPayment(Payment payment, LessonSlot lesson) {
        var Payment = new Payment();
        var teacher = lesson.getTeacher();
        return null;
    }
}
