package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.busticket.models.Location;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByName(String name);
}