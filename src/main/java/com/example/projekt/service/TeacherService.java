package com.example.projekt.service;

import com.example.projekt.dto.TeacherDetailsDto;
import com.example.projekt.dto.TeacherFilter;
import com.example.projekt.dto.TeacherWithRatingFilter;
import com.example.projekt.dto.UserDto;
import com.example.projekt.dto.response.*;
import com.example.projekt.exception.EmailAlreadyExistsException;
import com.example.projekt.model.*;
import com.example.projekt.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
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
    private final EntityManager entityManager;

    public Teacher addTeacher(UserDto teacherData) {
        if(userRepository.existsByEmail(teacherData.email())) {
            throw new EmailAlreadyExistsException(teacherData.username());
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
                .map(locationDto -> locationRepository.findLocationByTownAndDistrictIgnoreCase(locationDto.town(), locationDto.district())
                            .orElseGet(() -> locationRepository.save(new Location(locationDto.town(), locationDto.district()))))
                        .collect(Collectors.toList());

        teacher.setLocations(locationList);

        return teacherRepository.save(teacher);
    }

    public Teacher getByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public Teacher getById(UUID id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
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
                        }).toList(),
                new TeacherResponseDto(teacherRepository.getTeacherById(teacher.getId()))
        );
    }

    @Transactional
    public void pinStudentFromLesson(Long lessonId, Teacher teacher) {
        var lesson = lessonSlotRepository.findLessonSlotById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        var sessionTeacher = teacherRepository.getTeacherById(teacher.getId());
        if(!lesson.getTeacher().equals(sessionTeacher) || lesson.getState() != LessonState.COMPLETED){
            throw new EntityNotFoundException("Can't pin student from this lesson");
        }

        sessionTeacher.getStudents().add(new PinnedStudent(lesson.getStudent(), lesson.getSubject(), teacher));
        teacherRepository.save(teacher);
    }

    public void unpinStudent(Long pinId, Teacher teacher) {
        var pin = pinnedStudentRepository.findPinnedStudentById(pinId)
                .orElseThrow(() -> new EntityNotFoundException("Pinned student not found"));
        var sessionTeacher = teacherRepository.getTeacherById(teacher.getId());
        if(!sessionTeacher.getStudents().remove(pin)){
            throw new EntityNotFoundException("Sudent not pinned to this teacher");
        }
        teacherRepository.save(sessionTeacher);
    }

    public List<LessonSlot> findAllCompletedLessons(Teacher teacher){
        return lessonSlotRepository.findLessonSlotsByTeacherAndState(teacher, LessonState.COMPLETED);
    }

    public List<TeacherWithRatingFilter> findTeachers(TeacherFilter teacherFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeacherWithRatingFilter> teacherCriteria = cb.createQuery(TeacherWithRatingFilter.class);
        Root<Teacher> teacherRoot = teacherCriteria.from(Teacher.class);

        Join<Teacher, SubjectDetails> subjectDetails = teacherRoot.join("subjectDetails", JoinType.LEFT);
        Join<SubjectDetails, SubjectDict> subject = subjectDetails.join("subject", JoinType.LEFT);
        Join<SubjectDetails, SchoolPrice> schoolPrices = subjectDetails.join("schoolPrices", JoinType.LEFT);
        Join<SchoolPrice, SchoolDict> schoolDict = schoolPrices.join("school", JoinType.LEFT);
        Join<Teacher, Location> location = teacherRoot.join("locations", JoinType.LEFT);
        Join<Teacher, Rating> rating = teacherRoot.join("ratings", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        teacherCriteria.groupBy(teacherRoot.get("id"));

        if(teacherFilter.locationId() != null && !teacherFilter.locationId().isEmpty()){
            predicates.add(location.get("id").in(teacherFilter.locationId()));
        }

        if(teacherFilter.subjectId() != null){
            predicates.add(cb.equal(subject.get("id"), teacherFilter.subjectId()));
        }

        if(teacherFilter.schoolId() != null){
            predicates.add(cb.equal(schoolDict.get("id"), teacherFilter.schoolId()));
        }

        if(teacherFilter.minPrice() != null){
            if(teacherFilter.subjectId() != null){
                predicates.add(cb.and(
                        cb.equal(subject.get("id"), teacherFilter.subjectId()),
                        cb.greaterThanOrEqualTo(schoolPrices.get("price"), teacherFilter.minPrice())
                ));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(schoolPrices.get("price"), teacherFilter.minPrice()));
            }
        }

        if(teacherFilter.maxPrice() != null){
            if(teacherFilter.subjectId() != null){
                predicates.add(cb.and(
                        cb.equal(subject.get("id"), teacherFilter.subjectId()),
                        cb.lessThanOrEqualTo(schoolPrices.get("price"), teacherFilter.maxPrice())
                ));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(schoolPrices.get("price"), teacherFilter.maxPrice()));
            }
        }

        if(!predicates.isEmpty()){
            teacherCriteria.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        teacherCriteria.select(
                cb.construct(
                        TeacherWithRatingFilter.class,
                        teacherRoot,
                        cb.avg(rating.get("value")),
                        cb.count(rating.get("value"))
                )
        );

        teacherCriteria.distinct(true);

        var teachers = entityManager.createQuery(teacherCriteria)
                .setFirstResult(teacherFilter.page() * teacherFilter.size())
                .setMaxResults(teacherFilter.size())
                .getResultList();

        teachers.forEach(teacher -> {
            teacher.teacher().getSubjectDetails().forEach(subjectDetail -> {
                List<SchoolPrice> filteredSchoolPrice = subjectDetail.getSchoolPrices().stream()
                        .filter(sp -> (teacherFilter.schoolId() == null || sp.getSchool().getId() == teacherFilter.schoolId())).toList();
                subjectDetail.setSchoolPrices(filteredSchoolPrice);
            });
            List<SubjectDetails> filteredSubjectDetails = teacher.teacher().getSubjectDetails().stream()
                    .filter(sd ->
                            (teacherFilter.subjectId() == null || sd.getSubject().getId() == teacherFilter.subjectId())).toList();
            teacher.teacher().setSubjectDetails(filteredSubjectDetails);
        });

        return teachers;
    }
}
