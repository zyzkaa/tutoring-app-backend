package com.example.projekt.service;

import com.example.projekt.model.Location;
import com.example.projekt.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getLocationsByTown(String town) {
        return locationRepository.findLocationByTownContainingIgnoreCase(town);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
