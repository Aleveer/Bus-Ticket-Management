package com.myproject.busticket.repositories;

import org.springframework.stereotype.Repository;
import com.myproject.busticket.models.Schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByBus_id(int id);

}
