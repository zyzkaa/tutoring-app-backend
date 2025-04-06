package com.example.projekt.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public record LessonSlotDto(
        LocalDate date,
        List<LocalTime> timeList
){}
