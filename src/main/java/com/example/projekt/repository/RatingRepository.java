package com.example.projekt.repository;

import com.example.projekt.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Optional<Rating> findRatingById(int ratingId);
    Optional<Rating> findRatingByTeacherId(UUID teacherId);
    Optional<List<Rating>> findRatingsByTeacher_Username(String username);
}
