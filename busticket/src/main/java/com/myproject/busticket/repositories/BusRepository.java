package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.enums.BusStatus;
import com.myproject.busticket.models.Bus;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    Bus findByPlateNumber(String plateNumber);

    List<Bus> findByDriverId(int id);

    List<Bus> findByBusType(String busType);

    List<Bus> findByStatus(BusStatus status);

    // TODO: Custom query goes here if needed.
    // Example:
    @Query("SELECT b from buses where " + "b.id like %:keyword% " + "or b.plateNumber like %:keyword% "
            + "or b.busType like %:keyword% " + "or b.status like %:keyword% " + "or b.driverId like %:keyword% ")
    List<Bus> findByKeyword(String keyword);

}