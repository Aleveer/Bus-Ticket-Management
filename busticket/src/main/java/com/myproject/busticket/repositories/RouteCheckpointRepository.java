package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Route_Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteCheckpointRepository extends JpaRepository<Route_Checkpoint, Long> {
    @Query("select distinct rc.checkpointProvince from Route_Checkpoint rc")
    List<String> findAllCheckpointProvinces();

    @Query("select distinct rc.checkpointCity from Route_Checkpoint rc")
    List<String> findAllCheckpointCities();
}
