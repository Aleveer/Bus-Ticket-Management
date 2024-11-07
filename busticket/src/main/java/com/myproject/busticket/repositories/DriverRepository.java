package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Driver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    @EntityGraph(attributePaths = {"account"})
    Optional<Driver> findById(int driverId);

}
