package com.myproject.busticket.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;

@Controller
@RequestMapping("/admin")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteCheckpointService routeCheckpointService;

    @GetMapping("/route-detail/{routeCode}")
    public String getRouteDetails(@PathVariable String routeCode, Model model) {
        Route route = routeService.getRouteByCode(routeCode);
        List<Route_Checkpoint> checkpoints = routeCheckpointService.findByRoute(route);
        if (checkpoints.isEmpty()) {
            model.addAttribute("errorMessage", "No checkpoints found for this route.");
            return "redirect:/admin/route-management";
        }

        model.addAttribute("route", route);
        model.addAttribute("checkpoints", checkpoints);
        return "route-detail";
    }

    @GetMapping("/new-route")
    public String newRoute(Model model) {
        model.addAttribute("route", new Route());
        return "new-route";
    }

    @PostMapping("/new-route")
    public String saveRoute(Route route) {
        routeService.save(route);
        return "redirect:/admin/route-management";
    }
}