package com.example.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LessonSlot {
    public LessonSlot(Teacher teacher, LocalDate date, LocalTime time, LessonState lessonState) {
        this.teacher = teacher;
        this.date = date;
        this.time = time;
        this.state = lessonState;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    @Nullable
    @ManyToOne
    @JsonIgnore
    private User student;

    private LocalDate date;
    private LocalTime time;
    private double price;
    @ManyToOne
    private SchoolDict school;
    @ManyToOne
    private SubjectDict subject;

    @Enumerated(EnumType.STRING)
    private LessonState state;
}
