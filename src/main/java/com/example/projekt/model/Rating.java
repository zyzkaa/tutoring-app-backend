package com.example.projekt.model;

import com.example.projekt.dto.RatingDto;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Entity(name = "ratings")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Rating {
    public Rating(RatingDto ratingDto, User user, Teacher teacher) {
        this.user = user;
        this.teacher = teacher;
        BeanUtils.copyProperties(ratingDto, this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int value; // need to define a range

    @Column(length = 1000)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Teacher teacher;
}
