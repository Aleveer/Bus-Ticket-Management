package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByBus_id(int id);

    List<Seat> findByBus_idAndStatus(int id, String status);

    List<Seat> findByName(String name);

    // TODO: Implement more methods / queries here if needed.
}
