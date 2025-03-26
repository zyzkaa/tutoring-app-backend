package com.example.projekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class SubjectDict {
    public String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

}
