package com.example.projekt.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "subject_details")
@NoArgsConstructor
@Getter
@Setter
public class SubjectDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public SubjectDetails(SubjectDict subject, List<SchoolPrice> schoolPrices, Teacher teacher) {
        this.subject = subject;
        this.schoolPrices = schoolPrices;
        this.teacher = teacher;
        for(SchoolPrice sp : schoolPrices) {
            sp.setSubjectDetails(this);
        }
    }
    @ManyToOne
    private SubjectDict subject;

//    @OneToMany(cascade = CascadeType.ALL, )
    @OneToMany(mappedBy = "subjectDetails", cascade = CascadeType.ALL)
    private List<SchoolPrice> schoolPrices;

    @Column(columnDefinition = "boolean default false")
    private boolean maturaR; // name to change, field might go to Teacher entity

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;
}
