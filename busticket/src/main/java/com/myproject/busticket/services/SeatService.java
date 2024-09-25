package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Seat;
import com.myproject.busticket.repositories.SeatRepository;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    public Seat getById(int id) {
        return seatRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat deleteById(int id) {
        Seat seat = getById(id);
        if (seat != null) {
            seatRepository.deleteById(id);
        }
        return seat;
    }

    public List<Seat> getByBusId(int id) {
        return seatRepository.findByBus_id(id);
    }

    public List<Seat> getByName(String name) {
        return seatRepository.findByName(name);
    }

}
