package com.example.projekt.controller;

import com.example.projekt.dto.RatingDto;
import com.example.projekt.dto.RatingResponseDto;
import com.example.projekt.model.Rating;
import com.example.projekt.model.User;
import com.example.projekt.service.RatingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<RatingResponseDto> addRating(@RequestBody RatingDto ratingDto, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(new RatingResponseDto(ratingService.addRating(ratingDto, user)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Rating>> getAll(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }


}
