package com.myproject.busticket.controllers;

import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminPageController {
    TripService tripService;
    public AdminPageController(TripService tripService){
        this.tripService = tripService;
    }
    @GetMapping("/dashboard")
    public String dashBoardPage(){
        return "admin";
    }

    @GetMapping("/addTrip")
    public String addTripPage(Model model){
//        List<Trip> trips = tripService.findAll();
//        model.addAttribute("trips",trips);
        return "addTrip";
    }


}
