package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Route;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    Route findByCode(String code);

    Optional<Route> findByRouteId(int routeId);

    Optional<Route> findByName(String name);

    // Find By time but filter between time1 and time2:
    List<Route> findByTimeBetween(LocalDateTime time1, LocalDateTime time2);

    // Find By distance but filter between distance1 and distance2:
    List<Route> findByDistanceBetween(double distance1, double distance2);

    // More methods if needed
}
