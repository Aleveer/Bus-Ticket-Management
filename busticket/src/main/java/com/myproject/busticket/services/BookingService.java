package com.myproject.busticket.services;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.BookingRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public void createTicket(Booking booking) {
        bookingRepository.saveAndFlush(booking);
    }

    public List<Booking> findByTrip(Trip trip) {
        return bookingRepository.findByTrip(trip);
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public void deleteAll(List<Booking> bookings) {
        bookingRepository.deleteAll(bookings);
    }

    public Page<Booking> getAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByRoundTripId(String roundTripId) {
        return bookingRepository.findByRoundTripId(roundTripId);
    }
}
