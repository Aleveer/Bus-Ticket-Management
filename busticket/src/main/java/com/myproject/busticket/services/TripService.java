package com.myproject.busticket.services;

import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip findByTripId(int tripId){
        return tripRepository.findByTripId(tripId).orElseThrow();
    }

    public List<Trip> findAll(){
        return tripRepository.findAll();
    }
}
