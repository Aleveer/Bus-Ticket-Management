package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.TripHistory;

@Repository
public interface TripHistoryRepository extends JpaRepository<TripHistory, Integer> {

}
