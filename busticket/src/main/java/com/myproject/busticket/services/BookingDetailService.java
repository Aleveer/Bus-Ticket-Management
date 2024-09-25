package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.BookingDetail;
import com.myproject.busticket.repositories.BookingDetailRepository;

@Service
public class BookingDetailService {
    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    public List<BookingDetail> getAll() {
        return bookingDetailRepository.findAll();
    }

    public BookingDetail getById(int id) {
        return bookingDetailRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public BookingDetail save(BookingDetail bookingDetail) {
        return bookingDetailRepository.save(bookingDetail);
    }

    public BookingDetail deleteById(int id) {
        BookingDetail bookingDetail = getById(id);
        if (bookingDetail != null) {
            bookingDetailRepository.deleteById(id);
        }
        return bookingDetail;
    }

    public List<BookingDetail> getByBookingId(int id) {
        return bookingDetailRepository.findByBooking_id(id);
    }

    public List<BookingDetail> getByUserId(int id) {
        return bookingDetailRepository.findByUser_id(id);
    }

    public List<BookingDetail> getBySeatId(int id) {
        return bookingDetailRepository.findBySeat_id(id);
    }

    // TODO: Implement more methods if needed.
}
