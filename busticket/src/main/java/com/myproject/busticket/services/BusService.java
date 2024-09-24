package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.repositories.BusRepository;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    public Bus getById(int id) {
        return busRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus deleteById(int id) {
        Bus bus = getById(id);
        if (bus != null) {
            busRepository.deleteById(id);
        }
        return bus;
    }

    public Bus getByPlateNumber(String plateNumber) {
        return busRepository.findByPlateNumber(plateNumber);
    }

    public List<Bus> getByBusType(String busType) {
        return busRepository.findByBusType(busType);
    }

    public List<Bus> getByDriverId(int id) {
        return busRepository.findByUser_id(id);
    }

    // TODO: Implement more methods if needed.
}
