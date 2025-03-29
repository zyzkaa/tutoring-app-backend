package com.example.projekt.dto;

import com.example.projekt.model.Rating;
import lombok.Data;

import java.util.List;

@Data
public class RatingDto {
    private int value;
    private String teacherId;
    private List<Integer> subjectIds;
    private String content;
}
