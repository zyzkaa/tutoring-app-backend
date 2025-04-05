package com.example.projekt.repository;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.LessonState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LessonSlotRepository extends JpaRepository<LessonSlot, Integer> {
    Optional<LessonSlot> findLessonSlotById(Long id);

    Optional<List<LessonSlot>> findLessonSlotByIdIsIn(List<Integer> ids);

    List<LessonSlot> findLessonSlotByStateAndDateAndTimeAfter(LessonState state, LocalDate date, LocalTime timeAfter);

    List<LessonSlot> findLessonSlotByStateAndDate(LessonState lessonState, LocalDate now);

    @Query("SELECT COUNT(DISTINCT s.teacher.id) FROM LessonSlot s WHERE s.id IN :ids")
    int countDistinctTeacherByIdIn(Collection<Integer> ids);
}
