package com.example.projekt.repository;

import com.example.projekt.model.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<User> getUserById(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
