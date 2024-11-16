package com.myproject.busticket.controllers;

import com.myproject.busticket.dto.ScheduleDTO;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/home")
@Controller
public class IndexPageController {
    RouteCheckpointService routeCheckpointService;
    RouteService routeService;

    public IndexPageController(RouteCheckpointService routeCheckpointService, RouteService routeService) {
        this.routeCheckpointService = routeCheckpointService;
        this.routeService = routeService;
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        List<String> provinces = routeCheckpointService.getAllProvinces();
        List<String> cities = routeCheckpointService.getAllCities();
        model.addAttribute("tripType", "one-way");
        model.addAttribute("provinces", provinces);
        model.addAttribute("cities", cities);
        return "index";
    }

    @GetMapping("/schedule")
    public String schedulePage(Model model) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        List<Route> routes = routeService.getAll();
        for (Route route : routes) {
            ScheduleDTO schedule = new ScheduleDTO();
            schedule.setCode(route.getCode());
            schedule.setDepartureName(routeCheckpointService.findDepartureName(route.getCode()));
            schedule.setDropOffName(routeCheckpointService.findDropOffName(route.getCode()));
            schedule.setTime(route.getTime());
            schedule.setDistance(route.getDistance());
            schedules.add(schedule);
        }
        model.addAttribute("schedules", schedules);
        return "schedule";
    }

    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "about_us";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/search-ticket-info")
    public String searchTicketInfoPage() {
        return "search-ticket-info";
    }

    @GetMapping("/search-billing-info")
    public String searchBillingInfoPage() {
        return "search-billing-info";
    }

}
