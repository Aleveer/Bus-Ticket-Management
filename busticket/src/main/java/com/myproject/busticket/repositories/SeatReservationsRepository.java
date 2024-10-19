package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.Seat_Reservations;
import com.myproject.busticket.models.Trip;

@Repository
public interface SeatReservationsRepository extends JpaRepository<Seat_Reservations, Integer> {
    // More methods if needed
    Optional<Seat_Reservations> findBySeat(Bus_Seats seat);

    Optional<Seat_Reservations> findByTrip(Trip trip);

    Seat_Reservations findBySeatAndTrip(Bus_Seats seat, Trip trip);

    Seat_Reservations findBySeatIdAndTripId(int seatId, int tripId);

    // More methods if needed

}
