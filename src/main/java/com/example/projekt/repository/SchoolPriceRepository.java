package com.example.projekt.repository;

import com.example.projekt.model.SchoolPrice;
import com.example.projekt.model.SubjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolPriceRepository extends JpaRepository<SchoolPrice, Integer> {
    Optional<SchoolPrice> findSchoolPriceBySubjectDetailsAndSchoolId(SubjectDetails subjectDetails, Integer schoolId);
}
