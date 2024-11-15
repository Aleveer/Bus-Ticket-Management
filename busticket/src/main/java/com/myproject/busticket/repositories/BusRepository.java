package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    // Find bus by busId
    Bus findByPlate(String plate);
}
