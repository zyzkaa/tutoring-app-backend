package com.example.projekt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SchoolDict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
