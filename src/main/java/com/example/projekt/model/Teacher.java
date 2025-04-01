package com.example.projekt.model;

import com.example.projekt.dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "teachers")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Teacher extends User {
    public Teacher(UserDto userDto){
        super(userDto);
    }

    @Nullable
    @Column(length = 1000)
    private String description = "";

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<SubjectDetails> subjectDetails = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_TEACHER")
        );
    }
}
