package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.repositories.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Booking getById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking deleteById(int id) {
        Booking booking = getById(id);
        if (booking != null) {
            bookingRepository.deleteById(id);
        }
        return booking;
    }

    // TODO: Implement more methods if needed.
}
