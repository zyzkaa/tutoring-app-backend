package com.example.projekt.repository;

import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectDetailsRepository extends JpaRepository<SubjectDetails, Integer> {
    void deleteByTeacherId(UUID uuid);
}
