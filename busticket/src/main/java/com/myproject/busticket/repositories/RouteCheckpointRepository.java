package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.busticket.models.Route_Checkpoint;

public interface RouteCheckpointRepository extends JpaRepository<Route_Checkpoint, Integer> {
    @Query("select distinct rc.checkpointProvince from Route_Checkpoint rc")
    List<String> findAllCheckpointProvinces();

    @Query("select distinct rc.checkpointCity from Route_Checkpoint rc")
    List<String> findAllCheckpointCities();
}
