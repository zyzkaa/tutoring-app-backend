package com.example.projekt.model;

import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Rating {
    public Rating(int value, User user, Teacher teacher, List<SubjectDict> subjects, String content) {
        this.value = value;
        this.user = user;
        this.teacher = teacher;
        this.subjectDict = subjects;
        this.content = content;
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

    @ManyToMany()
    private List<SubjectDict> subjectDict;
}
