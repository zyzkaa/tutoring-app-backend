package com.example.projekt.controller;

import com.example.projekt.model.TestEntity;
import com.example.projekt.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Controller {
    private final TestService testService;

    @GetMapping("/all")
    public ResponseEntity<List<TestEntity>> getTest() {
        return ResponseEntity.ok(testService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<TestEntity> addTest(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(testService.add(testEntity));
    }
}
