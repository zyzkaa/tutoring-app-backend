package com.example.projekt.service;

import com.example.projekt.model.Teacher;
import com.example.projekt.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(PasswordHelper.encodePassword(teacher));
    }

}
