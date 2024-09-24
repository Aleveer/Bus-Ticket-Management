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

    List<Bus> findByUser_id(int id);

    List<Bus> findByBusType(String busType);

    List<Bus> findByStatus(BusStatus status);

    @Query("SELECT b FROM Bus b WHERE " +
            "CAST(b.id AS string) LIKE %:keyword% OR " +
            "b.plateNumber LIKE %:keyword% OR " +
            "b.busType LIKE %:keyword% OR " +
            "b.status LIKE %:keyword% OR " +
            "CAST(b.user.id AS string) LIKE %:keyword%")
    List<Bus> findByKeyword(String keyword);
}