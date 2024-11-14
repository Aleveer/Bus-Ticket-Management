package com.myproject.busticket.repositories;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationsRepository extends JpaRepository<SeatReservations, Integer>{
    List<SeatReservations> findByTrip_TripId(int tripId);
}
