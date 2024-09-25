package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.enums.ScheduleCheckpointStatus;
import com.myproject.busticket.models.ScheduleCheckpoint;
import com.myproject.busticket.repositories.ScheduleCheckpointRepository;

@Service
public class ScheduleCheckpointService {
    @Autowired
    private ScheduleCheckpointRepository scheduleCheckpointRepository;

    public List<ScheduleCheckpoint> getAll() {
        return scheduleCheckpointRepository.findAll();
    }

    public ScheduleCheckpoint getById(int id) {
        return scheduleCheckpointRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public ScheduleCheckpoint save(ScheduleCheckpoint scheduleCheckpoint) {
        return scheduleCheckpointRepository.save(scheduleCheckpoint);
    }

    public ScheduleCheckpoint deleteById(int id) {
        ScheduleCheckpoint scheduleCheckpoint = getById(id);
        if (scheduleCheckpoint != null) {
            scheduleCheckpointRepository.deleteById(id);
        }
        return scheduleCheckpoint;
    }

    public List<ScheduleCheckpoint> getByScheduleId(int id) {
        return scheduleCheckpointRepository.findBySchedule_id(id);
    }

    public List<ScheduleCheckpoint> getByCheckpointId(int id) {
        return scheduleCheckpointRepository.findByCheckpoint_id(id);
    }

    public ScheduleCheckpoint getByScheduleIdAndCheckpointId(int scheduleId, int checkpointId) {
        return scheduleCheckpointRepository.findBySchedule_idAndCheckpoint_id(scheduleId, checkpointId);
    }

    public void deleteByScheduleId(int id) {
        scheduleCheckpointRepository.deleteBySchedule_id(id);
    }

    public void deleteByCheckpointId(int id) {
        scheduleCheckpointRepository.deleteByCheckpoint_id(id);
    }

    public void deleteByScheduleIdAndCheckpointId(int scheduleId, int checkpointId) {
        scheduleCheckpointRepository.deleteBySchedule_idAndCheckpoint_id(scheduleId, checkpointId);
    }

    public List<ScheduleCheckpoint> getByArrivalTimeBetween(String startDate, String endDate) {
        return scheduleCheckpointRepository.findByArrivalTimeBetween(startDate, endDate);
    }

    public List<ScheduleCheckpoint> getByStatus(ScheduleCheckpointStatus status) {
        return scheduleCheckpointRepository.findByStatus(status);
    }
}
