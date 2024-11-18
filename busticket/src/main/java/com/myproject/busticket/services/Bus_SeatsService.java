package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.repositories.Bus_SeatsRepository;

@Service
public class Bus_SeatsService {
    @Autowired
    private Bus_SeatsRepository bus_SeatsRepository;

    public Bus_Seats save(Bus_Seats bus_Seats) {
        return bus_SeatsRepository.save(bus_Seats);
    }

    public void delete(Bus_Seats bus_Seats) {
        bus_SeatsRepository.delete(bus_Seats);
    }

    public List<Bus_Seats> getByBusPlate(Bus bus) {
        return bus_SeatsRepository.findByBus(bus);
    }
}