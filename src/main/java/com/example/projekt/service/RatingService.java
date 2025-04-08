package com.example.projekt.service;

import com.example.projekt.dto.RatingDto;
import com.example.projekt.model.Rating;
import com.example.projekt.model.User;
import com.example.projekt.repository.RatingRepository;
import com.example.projekt.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RatingService {
    private RatingRepository ratingRepository;
    private TeacherRepository teacherRepository;

    @Transactional
    public Rating addRating(RatingDto ratingDto, User user) {
        var teacher = teacherRepository.findById(UUID.fromString(ratingDto.teacherId()))
                .orElseThrow(() -> new RuntimeException("No teacher with id " + ratingDto.teacherId()));

        return ratingRepository.save(new Rating(ratingDto, user, teacher));
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByTeacherUsername(String username) {
        return ratingRepository.findRatingsByTeacher_Username(username)
                .orElse(new ArrayList<Rating>());
    }

    public void deleteById(User user, int id){
        var rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        if (!user.equals(rating.getUser())) {
            throw new RuntimeException("Rating cannot be deleted");
        }

        ratingRepository.delete(rating);
    }
}
