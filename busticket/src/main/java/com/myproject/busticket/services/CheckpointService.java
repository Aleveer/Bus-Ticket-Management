package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.CheckpointRepository;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointRepository checkpointRepository;
}
