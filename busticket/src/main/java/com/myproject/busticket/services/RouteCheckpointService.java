package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.RouteCheckpointRepository;

@Service
public class RouteCheckpointService {
    @Autowired
    private RouteCheckpointRepository routeCheckpointRepository;
}
