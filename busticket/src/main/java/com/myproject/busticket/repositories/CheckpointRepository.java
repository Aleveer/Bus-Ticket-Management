package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Checkpoint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {

    List<Checkpoint> findByRoute_id(int id);

    List<Checkpoint> findByLocation_id(int id);

    List<Checkpoint> findByLocation_idAndRoute_id(int locationId, int routeId);

    // TODO: Implement more methods / query if needed.
}
