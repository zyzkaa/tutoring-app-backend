package com.example.projekt.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
public class Availability {
    @Id
    public int id;

    @ManyToOne
    private WeekdayDict weekday;
}
