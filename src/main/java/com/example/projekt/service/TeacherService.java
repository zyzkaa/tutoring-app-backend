package com.example.projekt.service;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.UserDto;
import com.example.projekt.dto.response.*;
import com.example.projekt.exception.UsernameAlreadyExistsException;
import com.example.projekt.model.*;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SubjectDetailsRepository subjectDetailsRepository;
    private final SchoolDictRepository schoolDictRepository;
    private final SubjectDictRepository subjectDictRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final LessonSlotRepository lessonSlotRepository;
    private final PaymentRepository paymentRepository;
    private final PinnedStudentRepository pinnedStudentRepository;

    public Teacher addTeacher(UserDto teacherData) {
        if(userRepository.existsByUsername(teacherData.username())) {
            throw new UsernameAlreadyExistsException(teacherData.username());
        }

        return teacherRepository.save(PasswordHelper.encodePassword(new Teacher(teacherData)));
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }


    @Transactional
    public Teacher addDetails(TeacherDetailsDto teacherDetailsDto, Teacher teacher, HttpServletRequest request) {
        subjectDetailsRepository.deleteByTeacherId(teacher.getId());
        teacher.setDescription(teacherDetailsDto.description());

        teacher.setSubjectDetails(
                teacherDetailsDto.subjects().stream().
                        map(subjectDto -> {
                            var schoolPriceList = subjectDto.schoolPrices()
                                    .stream().map(schoolPriceDto -> new SchoolPrice(
                                            schoolDictRepository.findById(schoolPriceDto.schoolId())
                                                    .orElseThrow(() -> new EntityNotFoundException("School id not found")),
                                            schoolPriceDto.price()))
                                    .collect(Collectors.toList());
                            return new SubjectDetails(
                                    subjectDictRepository.findById(subjectDto.subjectId())
                                            .orElseThrow(() -> new EntityNotFoundException("Subject id not found")),
                                    schoolPriceList,
                                    teacher);
                        }).collect(Collectors.toList()));

        var locationList = teacherDetailsDto.locations()
                .stream()
                .map(locationDto -> locationRepository.findLocationByTownAndDistrict(locationDto.town(), locationDto.district())
                            .orElseGet(() -> locationRepository.save(new Location(locationDto.town(), locationDto.district()))))
                        .collect(Collectors.toList());

        teacher.setLocations(locationList);

        return teacherRepository.save(teacher);
    }

    public Teacher getByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public List<TeacherWithRatingAndPrice> getTeachersBySubject(Integer subjectId) {
//        return teacherRepository.findWithAvgRating();
        return null;
    }

    public TeacherProfileResponseDto getTeacherProfile(Teacher teacher){
        var todayLessons = lessonSlotRepository.getLessonSlotByStateAndDate(LessonState.BOOKED, LocalDate.now());
        var recentPayments = paymentRepository.findTop5ByOrderByDateAsc();
        var data = teacherRepository.findProfileData(teacher);
        var money = teacherRepository.getLessonsPricesSum(teacher);
        var students = pinnedStudentRepository.findPinnedStudentsByTeacher(teacher).stream().map(
                pinnedStudent -> new PinnedStudentResponseDto(
                        new ShortUserResponseDto(pinnedStudent.getStudent()),
                        pinnedStudent
                )
        ).collect(Collectors.toList());

        return new TeacherProfileResponseDto(
                data.ratings(),
                data.lessons(),
                money,
                data.students(),
                students,
                todayLessons,
                recentPayments.stream().map(
                        payment -> {
                            return new PaymentResponseDto(
                                    payment,
                                    new ShortUserResponseDto(payment.getLesson().getStudent())
                            );
                        }).toList()
        );
    }

    @Transactional
    public Teacher pinStudentFromLesson(Long lessonId, Teacher teacher) {
        var lesson = lessonSlotRepository.findLessonSlotById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        var sessionTeacher = teacherRepository.getTeacherById(teacher.getId());
        if(!lesson.getTeacher().equals(sessionTeacher) || lesson.getState() != LessonState.COMPLETED){
            throw new EntityNotFoundException("Can't pin student from this lesson");
        }

        sessionTeacher.getStudents().add(new PinnedStudent(lesson.getStudent(), lesson.getSubject(), teacher));
        return teacherRepository.save(teacher);
    }

    public Teacher unpinStudent(Long pinId, Teacher teacher) {
        var pin = pinnedStudentRepository.findPinnedStudentById(pinId)
                .orElseThrow(() -> new EntityNotFoundException("Pinned student not found"));
        var sessionTeacher = teacherRepository.getTeacherById(teacher.getId());
        if(!sessionTeacher.getStudents().remove(pin)){
            throw new EntityNotFoundException("Sudent not pinned to this teacher");
        }
        return teacherRepository.save(sessionTeacher);
    }
}
