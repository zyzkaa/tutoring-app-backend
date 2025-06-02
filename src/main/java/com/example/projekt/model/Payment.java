package com.example.projekt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Payment {
    public Payment(LessonSlot lesson) {
        date = LocalDateTime.now();
        this.lesson = lesson;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private LessonSlot lesson;
    private LocalDateTime date;
}
