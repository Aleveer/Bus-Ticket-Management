package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Checkpoint;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {

}
