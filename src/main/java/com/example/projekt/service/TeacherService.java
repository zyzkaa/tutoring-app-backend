package com.example.projekt.service;

import com.example.projekt.model.Teacher;
import com.example.projekt.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(PasswordHelper.encodePassword(teacher));
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

}
