package com.example.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PinnedStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User student;

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne
    private SubjectDict subject;

    public PinnedStudent(User student, SubjectDict subject, Teacher teacher) {
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
    }
}
