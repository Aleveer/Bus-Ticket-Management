package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.DriverBusAssignment;
import com.myproject.busticket.repositories.DriverBusAssignmentRepository;

@Service
public class DriverBusAssignmentService {
    @Autowired
    private DriverBusAssignmentRepository driverBusAssignmentRepository;

    public List<DriverBusAssignment> getAll() {
        return driverBusAssignmentRepository.findAll();
    }

    public DriverBusAssignment getById(int id) {
        return driverBusAssignmentRepository.findById(id).orElse(null);
    }

    public DriverBusAssignment save(DriverBusAssignment driverBusAssignment) {
        return driverBusAssignmentRepository.save(driverBusAssignment);
    }

    public DriverBusAssignment deleteById(int id) {
        DriverBusAssignment driverBusAssignment = getById(id);
        if (driverBusAssignment != null) {
            driverBusAssignmentRepository.deleteById(id);
        }
        return driverBusAssignment;
    }

    public List<DriverBusAssignment> findByDriverId(int id) {
        return driverBusAssignmentRepository.findByBusId(id);
    }

    public List<DriverBusAssignment> findByBusId(int id) {
        return driverBusAssignmentRepository.findByBusId(id);
    }

    public List<DriverBusAssignment> findByDriverIdAndAssignmentDate(int driverId, String startDate, String endDate) {
        return driverBusAssignmentRepository.findByDriverIdAndAssignmentDate(driverId, startDate, endDate);
    }

    public List<DriverBusAssignment> findByBusIdAndAssignmentDate(int busId, String startDate, String endDate) {
        return driverBusAssignmentRepository.findByBusIdAndAssignmentDate(busId, startDate, endDate);
    }

    public List<DriverBusAssignment> findByDriverIdAndBusIdAndAssignmentDate(int driverId, int busId, String startDate,
            String endDate) {
        return driverBusAssignmentRepository.findByDriverIdAndBusIdAndAssignmentDate(driverId, busId, startDate,
                endDate);
    }

    public List<DriverBusAssignment> findByAssignmentDateBetween(String startDate, String endDate) {
        return driverBusAssignmentRepository.findByAssignmentDateBetween(startDate, endDate);
    }

}
