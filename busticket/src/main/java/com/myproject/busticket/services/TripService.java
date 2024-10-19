package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.TripRepository;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
}
