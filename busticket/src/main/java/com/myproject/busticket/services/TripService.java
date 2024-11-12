package com.myproject.busticket.services;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.mapper.TripMapper;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.TripRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    TripRepository tripRepository;
    TripMapper tripMapper = Mappers.getMapper(TripMapper.class);
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public TripDTO getById(int tripId) {
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
