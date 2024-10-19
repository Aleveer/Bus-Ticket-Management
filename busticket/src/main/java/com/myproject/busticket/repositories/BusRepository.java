package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.myproject.busticket.enums.BusType;
import com.myproject.busticket.models.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    Optional<Bus> findByType(BusType type);

    Optional<Bus> findByPlate(String plate);

    void deleteByType(BusType type);

    // More methods if needed
}