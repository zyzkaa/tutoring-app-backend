package com.example.projekt.repository;

import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface SubjectDetailsRepository extends JpaRepository<SubjectDetails, Integer> {
    void deleteByTeacherId(UUID uuid);

    Optional<SubjectDetails> findSubjectDetailsByTeacherAndSubjectId(Teacher teacher, int subjectId);
}
