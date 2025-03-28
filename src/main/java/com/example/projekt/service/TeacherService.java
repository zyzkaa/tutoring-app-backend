package com.example.projekt.service;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.TeacherResponseDto;
import com.example.projekt.dto.UserRegisterDto;
import com.example.projekt.exception.UsernameAlreadyExistsException;
import com.example.projekt.model.SchoolPrice;
import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SubjectDetailsRepository subjectDetailsRepository;
    private final SchoolPriceRepository schoolPriceRepository;
    private final SchoolDictRepository schoolDictRepository;
    private final SubjectDictRepository subjectDictRepository;
    private final UserRepository userRepository;

    public TeacherResponseDto addTeacher(UserRegisterDto teacherData) {
        if(userRepository.existsByUsername(teacherData.getUsername())) {
            throw new UsernameAlreadyExistsException(teacherData.getUsername());
        }

        Teacher teacher =  teacherRepository.save(PasswordHelper.encodePassword(new Teacher(teacherData)));
        return new TeacherResponseDto(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }


    @Transactional
    public TeacherResponseDto addDetails(TeacherDetailsDto teacherDetailsDto) {
        Teacher teacher = teacherRepository.findByUsername("asdasd")
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        teacher.setDescription(teacherDetailsDto.getDescription());

        var subjectList = new ArrayList<SubjectDetails>();
        teacherDetailsDto.getSubjects().forEach(subject -> {
            var schoolPriceList = new ArrayList<SchoolPrice>();

            subject.getSchoolPrices().forEach(schoolPrice -> {
                var newSchoolPrice = new SchoolPrice(
                        schoolDictRepository.findById(schoolPrice.getSchoolId())
                                .orElseThrow(() -> new EntityNotFoundException("School id not found")),
                        schoolPrice.getPrice()
                );

                schoolPriceRepository.save(newSchoolPrice);
                schoolPriceList.add(newSchoolPrice);
            });

            var newSubject = new SubjectDetails(
                    subjectDictRepository.findById(subject.getSubjectId())
                            .orElseThrow(() -> new EntityNotFoundException("Subject id not found")),
                    schoolPriceList,
                    teacher
            );

            subjectDetailsRepository.save(newSubject);
            subjectList.add(newSubject);
        });
        teacher.setSubjectDetails(subjectList);

        teacherRepository.save(teacher);
        return new TeacherResponseDto(teacher);
    }

    public TeacherResponseDto getByUsername(String username) {
        return new TeacherResponseDto(teacherRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found")));
    }
}
