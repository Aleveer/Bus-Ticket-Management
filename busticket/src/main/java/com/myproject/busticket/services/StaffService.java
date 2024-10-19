package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.StaffRepository;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
}
