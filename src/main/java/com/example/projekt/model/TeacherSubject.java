package com.example.projekt.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
public class TeacherSubject {
    @Id
    private int id;

    @OneToOne
    private SubjectDict subjectDict;

    private float price;

    private boolean maturaR; // zmienic na nazwe angielska
}
