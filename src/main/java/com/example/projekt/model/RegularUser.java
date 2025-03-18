package com.example.projekt.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "regular_users")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class RegularUser extends UserEntity {

}
