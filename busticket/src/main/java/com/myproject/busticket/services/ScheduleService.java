package com.myproject.busticket.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.models.Schedule;
import com.myproject.busticket.repositories.ScheduleRepository;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public Schedule getById(int id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public Schedule save(Schedule schedule) {
        validateInputField(schedule);
        validateScheduleTime(schedule);
        return scheduleRepository.save(schedule);
    }

    public Schedule deleteById(int id) {
        Schedule schedule = getById(id);
        if (schedule != null) {
            scheduleRepository.deleteById(id);
        }
        return schedule;
    }

    public List<Schedule> getByBusId(int id) {
        return scheduleRepository.findByBus_id(id);
    }

    private void validateInputField(Schedule schedule) {
        if (schedule.getBus() == null) {
            throw new ValidationException("Bus is required");
        }

        if (schedule.getUser() == null) {
            throw new ValidationException("Driver is required");
        }

        if (schedule.getRoute() == null) {
            throw new ValidationException("Route is required");
        }

        if (schedule.getStartTime() == null) {
            throw new ValidationException("Start time is required");
        }

        if (schedule.getEndTime() == null) {
            throw new ValidationException("End time is required");
        }
    }

    private void validateScheduleTime(Schedule schedule) {
        LocalDateTime startTime = schedule.getStartTime();
        LocalDateTime endTime = schedule.getEndTime();
        LocalDateTime currentTime = LocalDateTime.now();

        if (startTime.equals(endTime)) {
            throw new ValidationException("Start time and end time cannot be the same");
        }

        if (startTime.isAfter(endTime)) {
            throw new ValidationException("Start time cannot be greater than end time");
        }

        if (endTime.isBefore(startTime)) {
            throw new ValidationException("End time cannot be less than start time");
        }

        if (startTime.isBefore(currentTime) || endTime.isBefore(currentTime)) {
            throw new ValidationException("Start time and end time cannot be less than the current time");
        }
    }
}
