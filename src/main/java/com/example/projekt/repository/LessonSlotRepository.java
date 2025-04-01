package com.example.projekt.repository;

import com.example.projekt.model.LessonSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LessonSlotRepository extends JpaRepository<LessonSlot, Integer> {
    Optional<LessonSlot> findLessonSlotById(Long id);

    Optional<List<LessonSlot>> findLessonSlotByIdIsIn(Collection<Long> ids);
}
