package com.myproject.busticket.services;

import com.myproject.busticket.mapper.DriverMapper;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.repositories.DriverRepository;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    DriverRepository driverRepository;
    DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getDriverById(int driverId) {
        return driverRepository.findById(driverId).orElse(null);
    }
}
