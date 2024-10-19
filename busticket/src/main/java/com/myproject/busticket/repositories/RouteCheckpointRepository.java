package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;

@Repository
public interface RouteCheckpointRepository extends JpaRepository<Route_Checkpoint, Integer> {
    Optional<Route_Checkpoint> findByRouteId(int routeId);

    Optional<Route_Checkpoint> findByCheckpointId(int checkpointId);

    Optional<Route_Checkpoint> findByRouteIdAndCheckpointId(int routeId, int checkpointId);

    Optional<Route_Checkpoint> findByRoute(Route route);

    Optional<Route_Checkpoint> findByRouteCode(String routeCode);

    // More methods if needed

}
