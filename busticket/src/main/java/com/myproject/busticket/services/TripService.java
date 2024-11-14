package com.myproject.busticket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.mapper.TripMapper;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.TripRepository;

@Service
public class TripService {
    TripRepository tripRepository;
    TripMapper tripMapper = Mappers.getMapper(TripMapper.class);
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public TripDTO findById(int tripId) {
        Optional<Trip> trip = tripRepository.findById(tripId);
        return tripMapper.entityToDTO(trip.get());
    }

    public List<Trip> findAll(){
        return tripRepository.findAll();
    }

    public List<TripDTO> searchTrip(String departure, String destination, LocalDateTime departureDate, int numberOfTickets){
        List<Trip> trip = tripRepository.findTrip(departure, destination, departureDate, numberOfTickets);
        return tripMapper.map(trip);
    }

    
}
