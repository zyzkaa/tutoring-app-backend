package com.example.projekt.service;

import com.example.projekt.dto.LessonSlotDto;
import com.example.projekt.model.*;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonSlotRepository lessonSlotRepository;
    private final SubjectDetailsRepository subjectDetailsRepository;
    private final SchoolPriceRepository schoolPriceRepository;
    private final UserRepository userRepository;

    public void addLessonSlots(List<LessonSlotDto> lessonSlotDto, Teacher teacher) {
        for (LessonSlotDto day : lessonSlotDto) {
            var date = day.date();
            for (LocalTime time : day.timeList()) {
                if(lessonSlotRepository.existsLessonSlotByDateAndTimeAndTeacher(date, time, teacher)){
                    throw new RuntimeException("Lesson slot for " + date + " " + time + "already exists");
                }
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


    public List<LessonSlot> bookLessonSlots(User user, List<Integer> idList, int subjectId, int schoolId, LessonMode mode) {
        if(lessonSlotRepository.countDistinctTeacherByIdIn(idList) > 1) {
            throw new RuntimeException("Cannot book lesson with diffrent teachers at once");
        }

        var slots = lessonSlotRepository.findLessonSlotByIdIsIn(idList);

        var teacher = slots.getFirst().getTeacher();
        var subjectDetails = subjectDetailsRepository.findSubjectDetailsByTeacherAndSubjectId(teacher, subjectId)
                .orElseThrow(() -> new EntityNotFoundException("No subject details found for teacher: " + teacher));
        var schoolPrice = schoolPriceRepository.findSchoolPriceBySubjectDetailsAndSchoolId(subjectDetails, schoolId)
                .orElseThrow(() -> new EntityNotFoundException("No school price found for subject: " + subjectDetails));
        var price = schoolPrice.getPrice();
        var subject = subjectDetails.getSubject();
        var school = schoolPrice.getSchool();

        slots = slots.stream().peek(slot -> {
            if(slot.getState() != LessonState.AVAILABLE) {
                throw new RuntimeException("Lesson with id " + slot.getId() + " is not available");
            }
            slot.setState(LessonState.PENDING);
            slot.setStudentConfirmed(true);
            slot.setStudent(user);
            slot.setSubject(subject);
            slot.setPrice(price);
            slot.setSchool(school);
            slot.setMode(mode);
        }).toList();

        return lessonSlotRepository.saveAll(slots);
    }

    public LessonSlot cancellLessonSlot(Long id, Teacher teacher) {
        var slot = lessonSlotRepository.findLessonSlotById(id)
                .orElseThrow(() -> new EntityNotFoundException("No lesson slot found with id: " + id));
        if(!slot.getTeacher().equals(teacher) || !(slot.getState() == LessonState.AVAILABLE || slot.getState() == LessonState.BOOKED)){
            throw new RuntimeException("Lesson with id " + slot.getId() + " is not available");
        }
        slot.setState(LessonState.CANCELLED);
        slot.setMode(null);
        return lessonSlotRepository.save(slot);
    }

    public List<LessonSlot> bookAsTeacher(List<Integer> idList, int subjectId, int schoolId, UUID userId, LessonMode mode){
        var user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user found for id: " + userId));
        var slots = this.bookLessonSlots(user, idList, subjectId, schoolId, mode)
                .stream()
                .peek(slot -> {
                    slot.setStudentConfirmed(false);
                    slot.setTeacherConfirmed(true);
                }).toList();
        return lessonSlotRepository.saveAll(slots);
    }

    public List<LessonSlot> getPendingLessonSlots(User user){
        var role = user.getAuthorities().toString();
        if(role.equals("ROLE_USER")){
            return lessonSlotRepository.findLessonSlotsByStudentAndState(user, LessonState.PENDING);
        } else if(role.equals("ROLE_TEACHER")){
            return lessonSlotRepository.findLessonSlotsByTeacherAndState((Teacher) user, LessonState.PENDING);
        }
        return null;
    }

    public void confirmSlots(User user, List<Integer> idList){
        var role = user.getAuthorities().toString();
        var slots = lessonSlotRepository.findLessonSlotByIdIsIn(idList);
        if(role.equals("ROLE_USER")){
            slots = slots.stream().peek(slot -> {
                if(!slot.getStudent().equals(user)) {
                    throw new RuntimeException("Cannot confirm slot with different student");
                }
                slot.setStudentConfirmed(true);
                if(slot.isTeacherConfirmed()){
                    slot.setState(LessonState.BOOKED);
                }
            }).toList();
        } else if(role.equals("ROLE_TEACHER")){
            slots = slots.stream().peek(slot -> {
                if(!slot.getTeacher().equals(user)) {
                    throw new RuntimeException("Cannot confirm slot with different student");
                }
                slot.setStudentConfirmed(true);
                if(slot.isStudentConfirmed()){
                    slot.setState(LessonState.BOOKED);
                }
            }).toList();
        }
        lessonSlotRepository.saveAll(slots);
    }
}
