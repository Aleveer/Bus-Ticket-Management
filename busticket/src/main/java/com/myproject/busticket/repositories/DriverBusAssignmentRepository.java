package com.myproject.busticket.repositories;

import com.myproject.busticket.models.DriverBusAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverBusAssignmentRepository extends JpaRepository<DriverBusAssignment, Integer> {

    List<DriverBusAssignment> findByUserId(int id);

    List<DriverBusAssignment> findByBusId(int id);

    // Search assignment date between start and end date input:
    @Query(value = "SELECT * FROM driver_bus_assignment WHERE driver_id = ?1 AND (assignment_date BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<DriverBusAssignment> findByDriverIdAndAssignmentDate(int driverId, String startDate, String endDate);

    // Search assignment date between start and end date input:
    @Query(value = "SELECT * FROM driver_bus_assignment WHERE bus_id = ?1 AND (assignment_date BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<DriverBusAssignment> findByBusIdAndAssignmentDate(int busId, String startDate, String endDate);

    // Search assignment date between start and end date input:
    @Query(value = "SELECT * FROM driver_bus_assignment WHERE driver_id = ?1 AND bus_id = ?2 AND (assignment_date BETWEEN ?3 AND ?4)", nativeQuery = true)
    List<DriverBusAssignment> findByDriverIdAndBusIdAndAssignmentDate(int driverId, int busId, String startDate,
            String endDate);

    // Search assignment date between start and end date input:
    @Query(value = "SELECT * FROM driver_bus_assignment WHERE assignment_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<DriverBusAssignment> findByAssignmentDateBetween(String startDate, String endDate);
}