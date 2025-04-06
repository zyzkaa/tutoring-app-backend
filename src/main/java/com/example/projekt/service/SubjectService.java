package com.example.projekt.service;

import com.example.projekt.model.SubjectDict;
import com.example.projekt.repository.SubjectDictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectDictRepository subjectDictRepository;

    public List<SubjectDict> getSubjects() {
        return subjectDictRepository.findAll();
    }
}
