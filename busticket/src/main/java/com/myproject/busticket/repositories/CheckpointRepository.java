package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Checkpoint;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {
    // @Query("SELECT c FROM Checkpoint c WHERE c.route.id = :routeId")
    // Optional<Checkpoint> findByRouteId(@Param("routeId") int routeId);

    Optional<Checkpoint> findByCheckpointId(int checkpointId);

    Optional<Checkpoint> findByPlaceName(String placeName);

    Optional<Checkpoint> findByAddress(String address);

    Optional<Checkpoint> findByPhone(String phone);

    Optional<Checkpoint> findByRegion(String region);

    // Add more methods if needed
}
