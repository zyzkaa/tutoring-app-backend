package com.example.projekt.model;


import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
public class TeacherSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    private SubjectDict subjectDict;

    private float price;

    private boolean maturaR; // zmienic na nazwe angielska
}
