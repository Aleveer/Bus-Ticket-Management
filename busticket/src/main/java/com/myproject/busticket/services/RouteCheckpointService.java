package com.myproject.busticket.services;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import com.myproject.busticket.repositories.RouteCheckpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteCheckpointService {
    RouteCheckpointRepository checkpointRepository;

    public RouteCheckpointService(RouteCheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    public List<String> getAllProvinces() {
        return checkpointRepository.findAllCheckpointProvinces();
    }

    public List<String> getAllCities() {
        return checkpointRepository.findAllCheckpointCities();
    }

    public Optional<Route_Checkpoint> findByRoute(Route route) {
        return checkpointRepository.findByRoute(route);
    }
}
