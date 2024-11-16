package com.myproject.busticket.services;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import com.myproject.busticket.repositories.RouteCheckpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteCheckpointService {
    RouteCheckpointRepository routeCheckpointRepository;

    public RouteCheckpointService(RouteCheckpointRepository routeCheckpointRepository) {
        this.routeCheckpointRepository = routeCheckpointRepository;
    }

    public List<String> getAllProvinces() {
        return routeCheckpointRepository.findAllCheckpointProvinces();
    }

    public List<String> getAllCities() {
        return routeCheckpointRepository.findAllCheckpointCities();
    }

    public List<Route_Checkpoint> findByRoute(Route route) {
        return routeCheckpointRepository.findByRoute(route);
    }

    public String findDepartureName(String routeCode) {
        return routeCheckpointRepository.findDepartureName(routeCode);
    }

    public String findDropOffName(String routeCode) {
        return routeCheckpointRepository.findDropOffName(routeCode);
    }

    public Route_Checkpoint save(Route_Checkpoint routeCheckpoint) {
        return checkpointRepository.save(routeCheckpoint);
    }
}
