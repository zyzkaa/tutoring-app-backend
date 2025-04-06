package com.example.projekt.dto;

import lombok.Data;

import java.util.List;

public record TeacherDetailsDto(
        String description,
        List<SubjectListDto> subjects,
        List<LocationDto> locations
){
    public record SubjectListDto(
            int subjectId,
            boolean maturaR,
            List<SchoolPriceDto> schoolPrices
    ){}

    public record SchoolPriceDto(
            int schoolId,
            float price
    ){}

    public record LocationDto(
            String town,
            String district
    ){}
}
