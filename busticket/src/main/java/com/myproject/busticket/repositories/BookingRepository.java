package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
}
