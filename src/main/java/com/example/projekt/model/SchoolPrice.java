package com.example.projekt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SchoolPrice {
    public SchoolPrice(SchoolDict school, double price) {
        this.school = school;
        this.price = price;
    }

    public SchoolPrice(SchoolDict school, double price, boolean matura) {
        this.school = school;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double price;

    @ManyToOne
    private SchoolDict school;
}
