package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.ControllerRepository;

@Service
public class ControllerService {
    @Autowired
    private ControllerRepository controllerRepository;
}
