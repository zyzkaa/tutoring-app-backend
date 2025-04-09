package com.example.projekt.controller;

import com.example.projekt.dto.RatingDto;
import com.example.projekt.dto.response.RatingResponseDto;
import com.example.projekt.model.Rating;
import com.example.projekt.model.User;
import com.example.projekt.service.RatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<RatingResponseDto> addRating(@RequestBody @Valid RatingDto ratingDto, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(new RatingResponseDto(ratingService.addRating(ratingDto, user)));
    }

    @GetMapping("/get-all") // delete this
    public ResponseEntity<List<Rating>> getAll(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable int id, @AuthenticationPrincipal User user){
        ratingService.deleteById(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
