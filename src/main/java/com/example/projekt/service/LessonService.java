package com.example.projekt.service;

import com.example.projekt.dto.LessonSlotDto;
import com.example.projekt.model.*;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonSlotRepository lessonSlotRepository;
    private final SubjectDictRepository subjectDictRepository;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final SubjectDetailsRepository subjectDetailsRepository;
    private final SchoolPriceRepository schoolPriceRepository;

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
        slot.setPrice(0.0);
        slot.setSubject(null);
        slot.setStudent(null);
        slot.setSchool(null);
        slot.setState(LessonState.AVAILABLE);
        lessonSlotRepository.save(slot);
    }

    public List<LessonSlot> bookLessonSlots(User user, List<Integer> idList, int subjectId, int schoolId) {
        if(lessonSlotRepository.countDistinctTeacherByIdIn(idList) > 1) {
//            System.out.println("wynik: " + lessonSlotRepository.countDistinctTeacherByIdIn(idList));
            throw new RuntimeException("Cannot book lesson with diffrent teachers at once");
        }

        var slots = lessonSlotRepository.findLessonSlotByIdIsIn(idList)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with one or more ids: " + idList));

        var teacher = slots.getFirst().getTeacher();
        var subjectDetails = subjectDetailsRepository.findSubjectDetailsByTeacherAndSubjectId(teacher, subjectId)
                .orElseThrow(() -> new EntityNotFoundException("No subject details found for teacher: " + teacher));
        var schoolPrice = schoolPriceRepository.findSchoolPriceBySubjectDetailsAndSchoolId(subjectDetails, schoolId)
                .orElseThrow(() -> new EntityNotFoundException("No school price found for subject: " + subjectDetails));
        var price = schoolPrice.getPrice();
        var subject = subjectDetails.getSubject();
        var school = schoolPrice.getSchool();

        for(LessonSlot slot : slots){
            if(slot.getState() != LessonState.AVAILABLE) {
                throw new RuntimeException("Lesson with id " + slot.getId() + " is not available");
            }
            slot.setState(LessonState.BOOKED);
            slot.setStudent(user);
            slot.setSubject(subject);
            slot.setPrice(price);
            slot.setSchool(school);
        }
        return lessonSlotRepository.saveAll(slots);
    }

    public LessonSlot cancellLessonSlot(Long id, Teacher teacher) {
        var slot = lessonSlotRepository.findLessonSlotById(id)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with id: " + id));
        if(!slot.getTeacher().equals(teacher) || !(slot.getState() == LessonState.AVAILABLE || slot.getState() == LessonState.BOOKED)){
            throw new RuntimeException("Lesson with id " + slot.getId() + " is not available");
        }
        slot.setState(LessonState.CANCELLED);
        return lessonSlotRepository.save(slot);
    }
}
