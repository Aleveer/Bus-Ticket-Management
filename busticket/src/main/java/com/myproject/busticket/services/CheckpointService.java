package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.repositories.CheckpointRepository;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointRepository checkpointRepository;

    public CheckpointService(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    public Page<Checkpoint> getAll(Pageable pageable) {
        return checkpointRepository.findAll(pageable);
    }

    public List<Checkpoint> findByPlaceName(String placeName) {
        return checkpointRepository.findByPlaceName(placeName);
    }

    public Checkpoint getById(int id) {
        return checkpointRepository.findById(id).orElse(null);
    }
}
