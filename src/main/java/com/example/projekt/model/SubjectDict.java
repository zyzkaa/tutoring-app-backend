package com.example.projekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SubjectDict {
    public String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

}
