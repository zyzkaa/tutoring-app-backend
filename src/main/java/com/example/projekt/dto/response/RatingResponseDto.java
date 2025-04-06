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
    public RatingResponseDto(Rating rating, User user, Teacher teacher){
        this(rating.getId(), rating.getValue(), rating.getContent(), new ShortUserResponseDto(user), new ShortUserResponseDto(teacher));
    }
}
