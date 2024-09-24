package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.busticket.models.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByLocationName(String locationName);

}
