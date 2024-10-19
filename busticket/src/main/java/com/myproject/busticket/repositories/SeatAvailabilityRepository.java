package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.Seat_Availability;
import com.myproject.busticket.models.Trip;

import java.util.Optional;

@Repository
public interface SeatAvailabilityRepository extends JpaRepository<Seat_Availability, Integer> {
    // More methods if needed
    Optional<Seat_Availability> findBySeat(Bus_Seats seat);

    Optional<Seat_Availability> findByTrip(Trip trip);

    Seat_Availability findBySeatAndTrip(Bus_Seats seat, Trip trip);

    Seat_Availability findBySeatIdAndTripId(int seatId, int tripId);

    // More methods if needed
}
