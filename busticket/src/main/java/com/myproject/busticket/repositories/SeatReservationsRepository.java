package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.models.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationsRepository extends JpaRepository<SeatReservations, Integer> {

    List<SeatReservations> findBySeat(Bus_Seats seat);

    List<SeatReservations> findByBooking(Booking booking);

    List<SeatReservations> findByTrip_TripId(int tripId);

    List<SeatReservations> findByTrip(Trip trip);

    @Query("SELECT s FROM SeatReservations s WHERE s.seat.id = :seatId AND s.trip.id = :tripId")
    List<SeatReservations> findBySeatIdAndTripId(@Param("seatId") int seatId, @Param("tripId") int tripId);
}
