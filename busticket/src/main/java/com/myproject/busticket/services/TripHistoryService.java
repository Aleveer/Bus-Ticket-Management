package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.TripHistory;
import com.myproject.busticket.repositories.TripHistoryRepository;

@Service
public class TripHistoryService {
    @Autowired
    private TripHistoryRepository tripHistoryRepository;

    public List<TripHistory> getAll() {
        return tripHistoryRepository.findAll();
    }

    public TripHistory getById(int id) {
        return tripHistoryRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public TripHistory save(TripHistory tripHistory) {
        return tripHistoryRepository.save(tripHistory);
    }

    public TripHistory deleteById(int id) {
        TripHistory tripHistory = getById(id);
        if (tripHistory != null) {
            tripHistoryRepository.deleteById(id);
        }
        return tripHistory;
    }

    public List<TripHistory> getByUserId(int id) {
        return tripHistoryRepository.findByUserId(id);
    }

    public List<TripHistory> getByBusId(int id) {
        return tripHistoryRepository.findByBusId(id);
    }

    public List<TripHistory> getByRouteId(int id) {
        return tripHistoryRepository.findByRouteId(id);
    }

    public List<TripHistory> getByStartTime(String startDate, String endDate) {
        return tripHistoryRepository.findByStartTime(startDate, endDate);
    }

    public List<TripHistory> getByEndTime(String startDate, String endDate) {
        return tripHistoryRepository.findByEndTime(startDate, endDate);
    }

    public List<TripHistory> getByStartTimeAndEndTime(String startDate, String endDate) {
        return tripHistoryRepository.findByStartTimeAndEndTime(startDate, endDate);
    }

    // TODO: Implement more methods / queries here if needed.
}
