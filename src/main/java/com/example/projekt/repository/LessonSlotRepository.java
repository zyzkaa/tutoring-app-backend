package com.example.projekt.repository;

import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.LessonState;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonSlotRepository extends JpaRepository<LessonSlot, Integer> {
    Optional<LessonSlot> findLessonSlotById(Long id);

    List<LessonSlot> findLessonSlotByIdIsIn(List<Integer> ids);

    List<LessonSlot> findLessonSlotByStateAndDateAndTimeAfter(LessonState state, LocalDate date, LocalTime timeAfter);

    List<LessonSlot> findLessonSlotByStateAndDate(LessonState lessonState, LocalDate now);

    @Query("SELECT COUNT(DISTINCT s.teacher.id) FROM LessonSlot s WHERE s.id IN :ids")
    int countDistinctTeacherByIdIn(Collection<Integer> ids);

    List<LessonSlot> findLessonSlotByStateAndDateAndTimeBefore(LessonState state, LocalDate date, LocalTime timeBefore);

    boolean existsLessonSlotByDateAndTimeAndTeacher(LocalDate date, LocalTime time, Teacher teacher);

    List<LessonSlot> getLessonSlotByStateAndDate(LessonState state, LocalDate date);

    List<LessonSlot> findLessonSlotsByStudentAndState(User student, LessonState state);

    List<LessonSlot> findLessonSlotsByTeacherAndState(Teacher teacher, LessonState state);

    List<LessonSlot> findLessonSlotsByStudentEmailAndState(String studentEmail, LessonState state);

    List<LessonSlot> findLessonSlotsByTeacherEmailAndState(String email, LessonState lessonState);

}
