package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    // @Query(value = "SELECT * FROM bus WHERE number_of_seat >=:numberOfTickets",
    // nativeQuery = true)
    // List<Bus> findBusByNumberOfSeat(@Param("numberOfTickets") int
    // numberOfTickets);

}
