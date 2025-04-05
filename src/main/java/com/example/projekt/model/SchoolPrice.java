package com.example.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SchoolPrice {
    public SchoolPrice(SchoolDict school, double price) {
        this.school = school;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double price;

    @ManyToOne
    private SchoolDict school;

    @ManyToOne
    @JsonIgnore
    private SubjectDetails subjectDetails;
}
