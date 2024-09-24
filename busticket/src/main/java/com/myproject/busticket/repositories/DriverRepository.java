package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Driver findByDriverName(String driverName);

}
