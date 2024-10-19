package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByAccount(Account account);

    void deleteByAccount(Account account);
}
