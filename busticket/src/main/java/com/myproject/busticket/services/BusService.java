package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.BusRepository;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

}
