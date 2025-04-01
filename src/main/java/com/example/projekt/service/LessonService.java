package com.example.projekt.service;

import com.example.projekt.dto.LessonSlotDto;
import com.example.projekt.model.LessonSlot;
import com.example.projekt.model.LessonState;
import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.repository.LessonSlotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonSlotRepository lessonSlotRepository;

    public void addLessonSlots(List<LessonSlotDto> lessonSlotDto, Teacher teacher) {
        for (LessonSlotDto day : lessonSlotDto) {
            var date = day.getDate();
            for (LocalTime time : day.getTimeList()) {
                lessonSlotRepository.save(new LessonSlot(teacher, date, time, LessonState.AVAILABLE));
            }
        }
    }

    public LessonSlot bookLessonSlot(Long id, User user) {
        var slot = lessonSlotRepository.findLessonSlotById(id)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with id: " + id));
        if(slot.getState() != LessonState.AVAILABLE) {
            throw new RuntimeException("Lesson slot not available");
        }
        slot.setState(LessonState.BOOKED);
        slot.setStudent(user);
        return lessonSlotRepository.save(slot);
    }

    private boolean canUnbook(LessonSlot slot, User user) {
        return slot.getStudent() != null && slot.getStudent().equals(user) && slot.getState() == LessonState.BOOKED;
    }

    public void unbookLessonSlot(Long id, User user){
        var slot = lessonSlotRepository.findLessonSlotById(id)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with id: " + id));
        if(!canUnbook(slot, user)) {
            throw new RuntimeException("Cannot be unbooked");
        }
        slot.setStudent(null);
        slot.setState(LessonState.AVAILABLE);
        lessonSlotRepository.save(slot);
    }

    public List<LessonSlot> bookLessonSlots(User user, List<Long> idList) {
        var slots = lessonSlotRepository.findLessonSlotByIdIsIn(idList)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with one or more ids: " + idList));
        for(LessonSlot slot : slots){
            if(slot.getState() != LessonState.AVAILABLE) {
                throw new RuntimeException("Lesson with id " + slot.getId() + " is not available");
            }
            slot.setState(LessonState.BOOKED);
            slot.setStudent(user);
        }
        return lessonSlotRepository.saveAll(slots);
    }
}
