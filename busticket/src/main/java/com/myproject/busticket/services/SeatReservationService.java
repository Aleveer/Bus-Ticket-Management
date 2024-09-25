package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.SeatReservation;
import com.myproject.busticket.repositories.SeatReservationRepository;

import java.util.List;

@Service
public class SeatReservationService {
    @Autowired
    private SeatReservationRepository seatReservationRepository;

    public List<SeatReservation> getAll() {
        return seatReservationRepository.findAll();
    }

    public SeatReservation getById(int id) {
        return seatReservationRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public SeatReservation save(SeatReservation seatReservation) {
        return seatReservationRepository.save(seatReservation);
    }

    public SeatReservation deleteById(int id) {
        SeatReservation seatReservation = getById(id);
        if (seatReservation != null) {
            seatReservationRepository.deleteById(id);
        }
        return seatReservation;
    }

}
