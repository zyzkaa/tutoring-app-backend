package com.example.projekt.repository;

import com.example.projekt.model.Location;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findLocationByTownAndDistrictIgnoreCase(@NotBlank String town, String district);

    List<Location> findLocationByTownContainingIgnoreCase(@NotBlank String town);
}
