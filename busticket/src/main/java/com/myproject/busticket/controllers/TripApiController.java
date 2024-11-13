package com.myproject.busticket.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.TripService;

@RestController
public class TripApiController {
    TripService tripService;

    public TripApiController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/api/trip/{tripId}")
    public TripDTO getById(@PathVariable int tripId) {
        TripDTO trip = tripService.findById(tripId);
        return trip;
    }

    @GetMapping("/api/trips")
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }
}
