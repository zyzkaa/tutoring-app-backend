package com.example.projekt.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity(name = "teachers")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Teacher extends User {

    @Nullable
    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "teacher")
    private List<SubjectDetails> subjectDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_TEACHER")
        );
    }
}
