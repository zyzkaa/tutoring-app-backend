package com.example.projekt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Teacher providerUser;

    @OneToMany()
    private List<SubjectDict> subjectDict;
}
