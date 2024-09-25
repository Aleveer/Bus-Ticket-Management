package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.ScheduleCheckpoint;
import com.myproject.busticket.enums.ScheduleCheckpointStatus;

@Repository
public interface ScheduleCheckpointRepository extends JpaRepository<ScheduleCheckpoint, Integer> {

    List<ScheduleCheckpoint> findBySchedule_id(int id);

    List<ScheduleCheckpoint> findByCheckpoint_id(int id);

    ScheduleCheckpoint findBySchedule_idAndCheckpoint_id(int scheduleId, int checkpointId);

    void deleteBySchedule_id(int id);

    void deleteByCheckpoint_id(int id);

    void deleteBySchedule_idAndCheckpoint_id(int scheduleId, int checkpointId);

    // Search between dates with arrival time:
    @Query(value = "SELECT sc FROM ScheduleCheckpoint sc WHERE sc.arrivalTime BETWEEN ?2 AND ?3", nativeQuery = true)
    List<ScheduleCheckpoint> findByArrivalTimeBetween(String startDate, String endDate);

    List<ScheduleCheckpoint> findByStatus(ScheduleCheckpointStatus status);
}
