package com.example.projekt.service;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.UserDto;
import com.example.projekt.exception.UsernameAlreadyExistsException;
import com.example.projekt.model.Location;
import com.example.projekt.model.SchoolPrice;
import com.example.projekt.model.SubjectDetails;
import com.example.projekt.model.Teacher;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
    private final SchoolDictRepository schoolDictRepository;
    private final SubjectDictRepository subjectDictRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public Teacher addTeacher(UserDto teacherData) {
        if(userRepository.existsByUsername(teacherData.getUsername())) {
            throw new UsernameAlreadyExistsException(teacherData.getUsername());
        }

        return teacherRepository.save(PasswordHelper.encodePassword(new Teacher(teacherData)));
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }


    @Transactional
    public Teacher addDetails(TeacherDetailsDto teacherDetailsDto, Teacher teacher, HttpServletRequest request) {
        if(request.getMethod().equals("PUT")) {
            subjectDetailsRepository.deleteByTeacherId(teacher.getId());
        }

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

                schoolPriceList.add(newSchoolPrice);
            });

            var newSubject = new SubjectDetails(
                    subjectDictRepository.findById(subject.getSubjectId())
                            .orElseThrow(() -> new EntityNotFoundException("Subject id not found")),
                    schoolPriceList,
                    teacher
            );

            subjectList.add(newSubject);
        });
        teacher.setSubjectDetails(subjectList);

        var locationList = new ArrayList<Location>();
        for(TeacherDetailsDto.LocationDto locationDto : teacherDetailsDto.getLocations()) {
            var location = locationRepository.findLocationByTownAndDistrict(locationDto.getTown(), locationDto.getDistrict())
                    .orElse(new Location(locationDto.getTown(), locationDto.getDistrict()));
            locationList.add(location);
        }
        teacher.setLocations(locationList); // will the new ones save?????????????

        return teacherRepository.save(teacher);
    }

    public Teacher getByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }
}
