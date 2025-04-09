package com.example.projekt.repository;

import com.example.projekt.dto.TeacherProfileDataDto;
import com.example.projekt.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID>, JpaSpecificationExecutor<Teacher>{
    Optional<Teacher> findByUsername(String username);

    @Query("""
    select new com.example.projekt.dto.TeacherProfileDataDto(
               count(distinct r.id),
               count(distinct ls.id),
               count(distinct ls.student.id)
           )    from Teacher t
    left join Rating r on r.teacher = t
    left join LessonSlot ls on ls.teacher = t
    where t = :teacher
    and ls.state = 'COMPLETED'
    group by t.id
""")
    TeacherProfileDataDto findProfileData(Teacher teacher);

    @Query("""
    select sum(ls.price) from Teacher t
    left join LessonSlot ls on ls.teacher = t
    where t = :teacher
    and ls.state = 'COMPLETED'
    group by t.id
""")
    Double getLessonsPricesSum(Teacher teacher);

    Teacher getTeacherById(UUID id);

    Optional<Teacher> findByEmail(String email);
}
