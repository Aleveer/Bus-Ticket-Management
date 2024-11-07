package com.myproject.busticket.services;

import com.myproject.busticket.models.Driver;
import com.myproject.busticket.repositories.DriverRepository;
import com.myproject.busticket.repositories.TripRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getDriverById(int driverId) {
        return driverRepository.findById(driverId).orElse(null);
    }
}
