package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.myproject.busticket.models.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    Optional<Trip> findByTripId(int tripId);

    Optional<Trip> findByRouteId(int routeId);

    Optional<Trip> findByBusId(int busId);

    Optional<Trip> findByDriverId(int driverId);

    Optional<Trip> findByControllerId(int controllerId);

    Optional<Trip> findByStaffId(int staffId);

    Optional<Trip> findByStatus(String status);

    // find by departure time and arrival time by using filter between time1 and
    // time2:
    Optional<Trip> findByDepartureTimeBetween(String time1, String time2);

    Optional<Trip> findByArrivalTimeBetween(String time1, String time2);

    // More methods if needed
}