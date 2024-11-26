package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Driver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    @EntityGraph(attributePaths = { "account" })
    Optional<Driver> findById(int driverId);

    List<Driver> findByAccount(Account account);
}
