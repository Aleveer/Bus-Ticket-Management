package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTrip(Trip trip);
}
