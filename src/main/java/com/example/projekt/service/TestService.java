package com.example.projekt.service;

import com.example.projekt.model.TestEntity;
import com.example.projekt.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public List<TestEntity> getAll(){
        return testRepository.findAll();
    }

    public TestEntity add(TestEntity testEntity){
        return testRepository.saveAndFlush(testEntity);
    }
}
