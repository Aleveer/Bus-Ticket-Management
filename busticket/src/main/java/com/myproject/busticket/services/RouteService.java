package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.repositories.RouteRepository;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findRouteByDistance(double min, double max) {
        return routeRepository.findRouteByDistance(min, max);
    }

    public Page<Route> getAll(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }
}
