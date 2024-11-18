package com.myproject.busticket.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Checkpoint;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {
    List<Checkpoint> findByPlaceName(String placeName);

    @Query("Select distinct c.province from Checkpoint c")
    List<String> getAllProvinces();

    @Query("Select distinct c.city from Checkpoint c")
    List<String> getAllCities();
}
