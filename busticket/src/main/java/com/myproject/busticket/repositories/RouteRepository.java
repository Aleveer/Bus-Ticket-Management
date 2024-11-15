package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query(value = "SELECT r FROM Route r WHERE r.distance BETWEEN :min AND :max", nativeQuery = true)
    List<Route> findRouteByDistance(@Param("min") double min, @Param("max") double max);

    // TODO: More methods here if needed
}
