package com.example.projekt.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "subject_details")
@NoArgsConstructor
@Getter
public class SubjectDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public SubjectDetails(SubjectDict subject, List<SchoolPrice> schoolPrices, Teacher teacher) {
        this.subject = subject;
        this.schoolPrices = schoolPrices;
        this.teacher = teacher;
    }
    @OneToOne
    private SubjectDict subject;

    @ManyToMany
    private List<SchoolPrice> schoolPrices;

    @Column(columnDefinition = "boolean default false")
    private boolean maturaR; // name to change, field might go to Teacher entity

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;
}
