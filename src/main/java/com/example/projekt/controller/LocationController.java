package com.example.projekt.controller;
import com.example.projekt.model.Location;
import com.example.projekt.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    public final LocationService locationService;

    @GetMapping("/{town}")
    public ResponseEntity<List<Location>> getLocationsByTown(@PathVariable String town) {
        return ResponseEntity.ok(locationService.getLocationsByTown(town));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
