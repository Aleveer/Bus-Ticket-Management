package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.TripHistory;

@Repository
public interface TripHistoryRepository extends JpaRepository<TripHistory, Integer> {

    public List<TripHistory> findByUserId(int id);

    public List<TripHistory> findByBusId(int id);

    public List<TripHistory> findByRouteId(int id);

    // Find between two dates: start_time and end_time in database:
    @Query(value = "SELECT * FROM trip_history WHERE start_time BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<TripHistory> findByStartTime(String startDate, String endDate);

    // Find between two dates: end_time and end_time in database:
    @Query(value = "SELECT * FROM trip_history WHERE end_time BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<TripHistory> findByEndTime(String startDate, String endDate);

    // Find between two dates: start_time and end_time in database:
    @Query(value = "SELECT * FROM trip_history WHERE start_time BETWEEN ?1 AND ?2 OR end_time BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<TripHistory> findByStartTimeAndEndTime(String startDate, String endDate);

    // TODO: Implement more methods / queries here if needed.
}
