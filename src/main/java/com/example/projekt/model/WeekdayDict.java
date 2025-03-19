package com.example.projekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WeekdayDict {
    @Id
    public int id;
    public String name;
}
