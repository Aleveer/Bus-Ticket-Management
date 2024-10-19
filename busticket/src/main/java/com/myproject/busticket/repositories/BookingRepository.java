package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.enums.TicketType;
import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBill(Bill bill);

    Optional<Booking> findByTicketType(TicketType ticketType);

    Optional<Booking> findById(int id);

    void deleteByBill(Bill bill);

    // More methods if needed

}
