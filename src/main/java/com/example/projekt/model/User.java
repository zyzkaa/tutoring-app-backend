package com.example.projekt.model;

import com.example.projekt.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Entity(name = "users")
@NoArgsConstructor
@Setter
@ToString(exclude = "password")
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements UserDetails, OidcUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Timestamp creationDate;

    @Transient
    private Map<String, Object> attributes = new HashMap<>();
    @Transient
    private OidcUserInfo oidcUserInfo;
    @Transient
    private OidcIdToken oidcIdToken;

    public User(UserDto userDto) {
        BeanUtils.copyProperties(userDto, this);
    }

    public User(OidcUser oidcUser) {
        this.username = oidcUser.getAttribute("given_name");
        this.email = oidcUser.getAttribute("email");
        this.password = "";
        this.oidcUserInfo = oidcUser.getUserInfo();
        this.oidcIdToken = oidcUser.getIdToken();
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(this.id, user.id);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Map<String, Object> getClaims() {
        return Map.of();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUserInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcIdToken;
    }

    @Override
    public String getName() {
        return email;
    }
}
