package com.myproject.busticket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.mapper.TripMapper;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.TripRepository;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    TripMapper tripMapper = Mappers.getMapper(TripMapper.class);

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public TripDTO findById(int tripId) {
        Optional<Trip> trip = tripRepository.findById(tripId);
        return tripMapper.entityToDTO(trip.get());
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public List<Trip> findByBus(Bus bus) {
        return tripRepository.findByBus(bus);
    }

    public List<TripDTO> searchTrip(String departure, String destination, LocalDateTime departureDate,
            int numberOfTickets) {
        List<Trip> trip = tripRepository.findTrip(departure, destination, departureDate, numberOfTickets);
        return tripMapper.map(trip);
    }

    public List<Trip> findTripByRouteCode(Route routeCode) {
        return tripRepository.findByRoute(routeCode);
    }

    public Trip findTripById(int tripId) {
        Optional<Trip> trip = tripRepository.findById(tripId);
        return trip.get();
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    public List<Trip> findConflictingTripsByBus(String busPlate, LocalDateTime departureTime,
            LocalDateTime arrivalTime) {
        List<Trip> waitingTrips = tripRepository.findAllWaitingTrips();
        return waitingTrips.stream()
                .filter(trip -> trip.getBus().getPlate().equals(busPlate))
                .filter(trip -> (trip.getDepartureTime().isBefore(arrivalTime)
                        && trip.getArrivalTime().isAfter(departureTime)) ||
                        (trip.getDepartureTime().isBefore(departureTime)
                                && trip.getArrivalTime().isAfter(departureTime))
                        ||
                        (trip.getDepartureTime().isAfter(departureTime) && trip.getArrivalTime().isBefore(arrivalTime)))
                .collect(Collectors.toList());
    }

    public List<Trip> findConflictingTripsByController(int controllerId, LocalDateTime departureTime,
            LocalDateTime arrivalTime) {
        List<Trip> waitingTrips = tripRepository.findAllWaitingTrips();
        return waitingTrips.stream()
                .filter(trip -> trip.getController().getId() == controllerId)
                .filter(trip -> (trip.getDepartureTime().isBefore(arrivalTime)
                        && trip.getArrivalTime().isAfter(departureTime)) ||
                        (trip.getDepartureTime().isBefore(departureTime)
                                && trip.getArrivalTime().isAfter(departureTime))
                        ||
                        (trip.getDepartureTime().isAfter(departureTime) && trip.getArrivalTime().isBefore(arrivalTime)))
                .collect(Collectors.toList());
    }

    public List<Trip> findConflictingTripsByDriver(int driverId, LocalDateTime departureTime,
            LocalDateTime arrivalTime) {
        List<Trip> waitingTrips = tripRepository.findAllWaitingTrips();
        return waitingTrips.stream()
                .filter(trip -> trip.getDriver().getDriverId() == driverId)
                .filter(trip -> (trip.getDepartureTime().isBefore(arrivalTime)
                        && trip.getArrivalTime().isAfter(departureTime)) ||
                        (trip.getDepartureTime().isBefore(departureTime)
                                && trip.getArrivalTime().isAfter(departureTime))
                        ||
                        (trip.getDepartureTime().isAfter(departureTime) && trip.getArrivalTime().isBefore(arrivalTime)))
                .collect(Collectors.toList());
    }

    public List<Trip> findUpcomingTrips(LocalDateTime currentTime) {
        return tripRepository.findUpcomingTrips(currentTime);
    }
}
