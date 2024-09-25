package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.repositories.RouteRepository;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public Route getById(int id) {
        return routeRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Route save(Route route) {
        return routeRepository.save(route);
    }

    public Route deleteById(int id) {
        Route route = getById(id);
        if (route != null) {
            routeRepository.deleteById(id);
        }
        return route;
    }

}
