package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.repositories.CheckpointRepository;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointRepository checkpointRepository;

    public List<Checkpoint> getAll() {
        return checkpointRepository.findAll();
    }

    public Checkpoint getById(int id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Checkpoint save(Checkpoint checkpoint) {
        return checkpointRepository.save(checkpoint);
    }

    public Checkpoint deleteById(int id) {
        Checkpoint checkpoint = getById(id);
        if (checkpoint != null) {
            checkpointRepository.deleteById(id);
        }
        return checkpoint;
    }

    public List<Checkpoint> getByRouteId(int id) {
        return checkpointRepository.findByRoute_id(id);
    }

    public List<Checkpoint> getByLocationId(int id) {
        return checkpointRepository.findByLocation_id(id);
    }

    public List<Checkpoint> getByLocationIdAndRouteId(int locationId, int routeId) {
        return checkpointRepository.findByLocation_idAndRoute_id(locationId, routeId);
    }

    // TODO: Implement more methods if needed.
}
