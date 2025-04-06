package com.example.projekt.dto.response;

import com.example.projekt.model.Rating;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;


public record RatingResponseDto(
        int id,
        int value,
        String content,
        ShortUserResponseDto user,
        ShortUserResponseDto teacher
) {
    public RatingResponseDto(Rating rating){
        this(rating.getId(), rating.getValue(), rating.getContent(), new ShortUserResponseDto(rating.getUser()), new ShortUserResponseDto(rating.getTeacher()));
    }
}
