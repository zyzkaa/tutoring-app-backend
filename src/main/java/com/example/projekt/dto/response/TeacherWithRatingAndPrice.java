package com.example.projekt.dto.response;

import java.util.UUID;

public record TeacherWithRatingAndPrice(UUID id, String firstname, String lastname, Double avgRating, Double price){
}
