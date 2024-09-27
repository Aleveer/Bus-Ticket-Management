package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.SeatReservation;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Integer> {

}