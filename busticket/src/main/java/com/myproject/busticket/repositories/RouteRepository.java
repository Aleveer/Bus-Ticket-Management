package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}
