package com.example.projekt.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "teachers")
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Teacher extends User {

    @Nullable
    @Column(length = 1000)
    private String description;

    @OneToMany()
    private List<TeacherSubject> subjects;
}
