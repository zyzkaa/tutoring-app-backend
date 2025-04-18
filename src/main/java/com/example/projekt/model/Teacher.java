package com.example.projekt.model;

import com.example.projekt.dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Teacher extends User {
    public Teacher(UserDto userDto){
        super(userDto);
    }

    public Teacher(OidcUser oidcUser){
        super(oidcUser);
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

    @ManyToMany
    private List<Location> locations = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PinnedStudent> students = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Rating> ratings = new ArrayList<>();
}
