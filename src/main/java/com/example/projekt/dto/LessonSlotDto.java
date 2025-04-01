package com.example.projekt.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LessonSlotDto {
    private LocalDate date;
    private List<LocalTime> timeList;
}
