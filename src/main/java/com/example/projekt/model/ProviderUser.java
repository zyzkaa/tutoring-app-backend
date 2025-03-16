package com.example.projekt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProviderUser extends UserEntity {

    private float hourlyRate;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Subject> subjects;
}
