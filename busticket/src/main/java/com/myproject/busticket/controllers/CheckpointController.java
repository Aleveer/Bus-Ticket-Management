package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;

@Controller
@RequestMapping("/admin")
public class CheckpointController {
    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private RouteCheckpointService routeCheckpointService;

    @Autowired
    private RouteService routeService;

}
