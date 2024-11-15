package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    // @Query(value = "SELECT * FROM bus WHERE number_of_seat >=:numberOfTickets",
    // nativeQuery = true)
    // List<Bus> findBusByNumberOfSeat(@Param("numberOfTickets") int
    // numberOfTickets);

}
