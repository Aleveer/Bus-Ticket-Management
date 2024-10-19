package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.DriverRepository;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
}
