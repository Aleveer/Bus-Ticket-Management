package com.myproject.busticket.services;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    public void createTicket(Booking booking) {
        bookingRepository.saveAndFlush(booking);
    }
}
