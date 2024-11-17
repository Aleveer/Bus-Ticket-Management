package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import java.util.List;

@Repository
public interface Bus_SeatsRepository extends JpaRepository<Bus_Seats, Integer> {

    List<Bus_Seats> findByBus(Bus bus);
}
