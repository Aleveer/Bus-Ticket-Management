package com.myproject.busticket.validations;

import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.RouteService;

public class RouteCheckpointValidation {
    // Check if the code is valid and exist in database:
    @Autowired
    private RouteService routeService;

    @Autowired
    private CheckpointService checkpointService;

}
