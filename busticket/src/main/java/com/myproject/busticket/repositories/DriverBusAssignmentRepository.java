package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.DriverBusAssignment;

@Repository
public interface DriverBusAssignmentRepository extends JpaRepository<DriverBusAssignment, Integer> {

}
