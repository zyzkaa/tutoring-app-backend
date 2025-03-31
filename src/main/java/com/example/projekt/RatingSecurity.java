package com.example.projekt;

import com.example.projekt.model.User;
import com.example.projekt.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingSecurity {
    private RatingRepository ratingRepository;

    public boolean isOwner(User user, int ratingId) {
        var rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        return user.equals(rating.getUser());
    }
}
