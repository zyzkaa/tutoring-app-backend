package com.example.projekt.service;

import com.example.projekt.dto.RatingDto;
import com.example.projekt.dto.RatingResponseDto;
import com.example.projekt.model.Rating;
import com.example.projekt.model.SubjectDict;
import com.example.projekt.model.User;
import com.example.projekt.repository.RatingRepository;
import com.example.projekt.repository.SubjectDictRepository;
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
    private SubjectDictRepository subjectDictRepository;

    @Transactional
    public RatingResponseDto addRating(RatingDto ratingDto, User user) {
        var subjects = new ArrayList<SubjectDict>();
        for (Integer subjectId : ratingDto.getSubjectIds()) {
            subjects.add(subjectDictRepository.findById(subjectId).orElseThrow());
        }


        var teacher = teacherRepository.findById(UUID.fromString(ratingDto.getTeacherId()))
                .orElseThrow();


        var newRating  = new Rating(
                ratingDto.getValue(),
                user,
                teacher,
                subjects,
                ratingDto.getContent()
        );

        ratingRepository.save(newRating);
        return new RatingResponseDto(newRating);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<RatingResponseDto> getRatingsByTeacherUsername(String username) {
        var ratings = ratingRepository.findRatingsByTeacher_Username(username)
                .orElse(null);

        if (ratings == null) { return null; }

        var ratingResponseDtos = new ArrayList<RatingResponseDto>();

        for(Rating rating : ratings){
            ratingResponseDtos.add(new RatingResponseDto(rating));
        }

        return ratingResponseDtos;
    }
}
