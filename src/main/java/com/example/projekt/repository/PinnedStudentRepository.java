package com.example.projekt.repository;

import com.example.projekt.model.PinnedStudent;
import com.example.projekt.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PinnedStudentRepository extends JpaRepository<PinnedStudent, Long> {
    Optional<PinnedStudent> findPinnedStudentById(Long id);

    List<PinnedStudent> findPinnedStudentsByTeacher(Teacher teacher);
}
