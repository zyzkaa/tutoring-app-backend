package com.example.projekt.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDetailsDto {
    @Data
    public static class SchoolPriceDto{
        private int schoolId;
        private float price;
    }

    @Data
    public static class SubjectListDto {
        private int subjectId;
        private List<SchoolPriceDto> schoolPrices;
        private boolean isMaturaR; // nazwa do zmiany
    }

    private String description;
    private List<SubjectListDto> subjects;
}
