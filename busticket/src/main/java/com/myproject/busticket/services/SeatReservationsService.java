package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.SeatReservationsRepository;

@Service
public class SeatReservationsService {
    @Autowired
    private SeatReservationsRepository seatReservationsRepository;
}
