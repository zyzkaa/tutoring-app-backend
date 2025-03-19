package com.example.projekt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teacher extends User {

    private float hourlyRate;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherSubject> subjects;
}
