package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;

@Repository
public interface BusSeatsRepository extends JpaRepository<Bus_Seats, Integer> {
    Optional<Bus_Seats> findByBus(Bus bus);

    Optional<Bus_Seats> findBySeatNumber(int seatNumber);

    Optional<Bus_Seats> findById(int id);

    void deleteByBus(Bus bus);

    void deleteBySeatName(String seatName);

    // More methods if needed

}
