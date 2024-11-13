package com.myproject.busticket.services;

import com.myproject.busticket.repositories.RouteCheckpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
